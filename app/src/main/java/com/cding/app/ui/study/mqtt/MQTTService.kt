package com.cding.app.ui.study.mqtt

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.TimeUtils
import com.cding.app.R
import com.cding.app.CApp
import com.cding.app.utils.DeviceUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.openssl.PEMParser
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.io.BufferedInputStream
import java.io.InputStream
import java.io.InputStreamReader
import java.security.KeyStore
import java.security.Security
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManagerFactory


/**
 *
 */
class MQTTService : Service() {
    companion object{
        lateinit var mqttClient: MqttAndroidClient
        lateinit var mqttConnectionOptions: MqttConnectOptions

        //MQTT
//    val host = "tcp://47.96.13.111:1883" //协议+IP+端口
        val host = "ssl://47.96.13.111:8883"
        val userName = "admin"
        val password = "admin@20240221"

        val TOPIC_PUBLISH_ELECTRIC = "battery_power"
        val TOPIC_MEDICAL = "message_medical" //用药提醒
        val TOPIC_UNBIND = "message_unbind"  //解绑提醒

        var clientId = DeviceUtils.getDeviceId(CApp.instance)
    }

    private var mqttCallback = object: MqttCallback {
        override fun connectionLost(cause: Throwable?) {
            //失去连接
            MainScope().launch {
                delay(2000)
                doClientConnect()
            }
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            //收到主题消息
            var msgContent = String(message!!.payload)
            var mqttContent = GsonUtils.fromJson(msgContent, MqttMsgBean::class.java)
            when(topic){
                TOPIC_MEDICAL -> {

                }
                TOPIC_UNBIND -> {

                }
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            //客户端发布的消息已经发布成功
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        initMqtt()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        try {
            if(mqttClient.isConnected){
                mqttClient.disconnect()
                mqttClient.unregisterResources()
            }
        }catch (e: Exception){
            e.printStackTrace()
        }
        super.onDestroy()
    }

    private fun initMqtt(){
        mqttClient = MqttAndroidClient(this@MQTTService, host, clientId)
        mqttClient.setCallback(mqttCallback)

        mqttConnectionOptions = MqttConnectOptions()
        mqttConnectionOptions.isCleanSession = true //在重新连接时，是否清理缓存
        mqttConnectionOptions.connectionTimeout = 10 //超时时间
        mqttConnectionOptions.keepAliveInterval = 20 //心跳包间隔
        mqttConnectionOptions.userName = userName //用户名
        mqttConnectionOptions.password = password.toCharArray() //密码
//        mqttConnectionOptions.willMessage //客户端断开连接之后会发送此遗嘱消息，一般可用作判断某客户端已经离线
        setSSLConnection()

        doClientConnect()
    }

    private fun doClientConnect(){
        if(!mqttClient.isConnected){
            mqttClient.connect(mqttConnectionOptions, null, iMqttActionListener)
        }
    }

    var iMqttActionListener = object: IMqttActionListener{
        override fun onSuccess(asyncActionToken: IMqttToken?) {
            //连接成功
            mqttClient.subscribe(TOPIC_MEDICAL, 1)
            mqttClient.subscribe(TOPIC_UNBIND, 1)
        }

        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
            //连接失败
            MainScope().launch {
                delay(2000)
                doClientConnect()
            }
        }
    }

    fun publishTest(){
        val isCharge = true
        var mqttMsgBean = MqttUploadBean(
            100001, //代表topic
            clientId,
            TimeUtils.getSafeDateFormat("yyyy-MM-dd HH:mm:ss").format(TimeUtils.getNowDate()),
            60,
            if (isCharge) 1 else 0
        )
        publish(GsonUtils.toJson(mqttMsgBean))
    }

    fun publish(msg: String){
        val topic = TOPIC_PUBLISH_ELECTRIC
        //qos全称Quality of Service（服务质量）
        // 0--最多送达一次，消息有可能在传输过程中丢失;
        // 1--至少送达一次，消息至少保证送达到接收者一次，可能会送达多次;
        // 2--恰好送达一次，消息只会送达一次到接收方，不会出现多次或者0次的情况.
        val qos = 0
        val retained = false
        try {
            if (mqttClient != null) {
                mqttClient?.publish(topic, msg.toByteArray(), qos, retained)
            }
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    //双向校验
    private fun setSSLConnection(){
        var caCrtFile: InputStream? = null
        var clientCrtFile: InputStream? = null
        var keyFile: InputStream? = null
        try {
            caCrtFile = CApp.instance.getResources().openRawResource(R.raw.ca)
            clientCrtFile = CApp.instance.getResources().openRawResource(R.raw.client)
            keyFile = CApp.instance.getResources().openRawResource(R.raw.clientkey)
            mqttConnectionOptions.socketFactory = getSocketFactory(caCrtFile, clientCrtFile, keyFile, password)
        } catch (e: Exception) {
            e.printStackTrace()
        }finally {
            if(caCrtFile != null){
                caCrtFile.close()
                caCrtFile = null
            }
            if(clientCrtFile != null){
                clientCrtFile.close()
                clientCrtFile = null
            }
            if(keyFile != null){
                keyFile.close()
                keyFile = null
            }
        }
    }

    private fun getSocketFactory(
        caCrtFile: InputStream?,
        crtFile: InputStream?,
        keyFile: InputStream?,
        password: String
    ): SSLSocketFactory? {
        var sslSocketFactory: SSLSocketFactory? = null
        try {
            Security.addProvider(BouncyCastleProvider())

            // load CA certificate
            var caCert: X509Certificate? = null
            var bis = BufferedInputStream(caCrtFile)
            val cf = CertificateFactory.getInstance("X.509")
            while (bis.available() > 0) {
                caCert = cf.generateCertificate(bis) as X509Certificate
            }

            // load client certificate
            bis = BufferedInputStream(crtFile)
            var cert: X509Certificate? = null
            while (bis.available() > 0) {
                cert = cf.generateCertificate(bis) as X509Certificate
            }

            // load client private cert
            val pemParser = PEMParser(InputStreamReader(keyFile))
            val keyPair = pemParser.readObject()
            val converter = JcaPEMKeyConverter().setProvider("BC")
//          val key: KeyPair = converter.getKeyPair(keyPair as PEMKeyPair)
            val privateKey = converter.getPrivateKey(keyPair as PrivateKeyInfo)
            pemParser.close()

            val caKs = KeyStore.getInstance(KeyStore.getDefaultType())
            caKs.load(null, null)
            caKs.setCertificateEntry("ca-certificate", caCert)
            val tmf = TrustManagerFactory.getInstance("X509")
            tmf.init(caKs)
            val ks = KeyStore.getInstance(KeyStore.getDefaultType())
            ks.load(null, null)
            ks.setCertificateEntry("certificate", cert)
            ks.setKeyEntry("private-key", privateKey, password.toCharArray(), arrayOf<Certificate>(cert!!))
            val kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm())
            kmf.init(ks, password.toCharArray())

            val context = SSLContext.getInstance("TLSv1.2")
            context.init(kmf.keyManagers, tmf.trustManagers, null)
            sslSocketFactory = context.socketFactory
        }catch(e: Exception){
            e.printStackTrace()
            LogUtils.d("=========sslSocketFactory Exception:${e.printStackTrace()}")
        } finally {
            return sslSocketFactory
            sslSocketFactory = null
        }
    }

}