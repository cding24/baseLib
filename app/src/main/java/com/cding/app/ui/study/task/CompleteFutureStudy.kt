package com.cding.app.ui.study.task

import android.os.Bundle
import com.cding.app.databinding.ActivityCoroutineStudyBinding
import com.cding.app.ui.study.RxViewModel
import com.cding.common.base.BaseVMActivity
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import java.util.concurrent.TimeUnit


/**
 *  异步转同步
 *
 */
class CompleteFutureStudy : BaseVMActivity<RxViewModel, ActivityCoroutineStudyBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        downloadSliceSync()
    }

    /**
     * 通过CountDownLatch异步转同步
     */
    private fun downloadSliceSync(): String{
        val future = CallbackFuture<String>()
        //任务请求
        fun onSuccess(){
            future.set("https://www.baidu.com/file/11111111111111.png")
        }
        fun onFuture(){
            future.set("失败")
        }

        try{
            return future.get(10, TimeUnit.SECONDS) ?: "超时了"
        }catch (e: Exception){
            e.printStackTrace()
            return "出错！"
        }
    }


    private fun CallableOrRunnable(){
        //callable 和 runnable
        val callable = Callable<String>(){
           return@Callable ""
        }
        val runnable = Runnable {
            // ...
        }

        //java8中提供
//        val future = CompletableFuture<String>{
//        }

//        RunnableFuture-------Runnable和Future接口的继承接口
//        FutureTask<String>
        val executorService = Executors.newSingleThreadExecutor()
        val futureTask = FutureTask<String>(Callable<String>(){
            return@Callable "美丽少年"
        })
//        executorService.submit(futureTask)
        executorService.execute(futureTask)
        val result = futureTask.get()

    }

    /*
    * Runnable fun prototype
    @FunctionalInterface
    public interface Runnable {
        public abstract void run();
    }
    */
    /*
    * Callable fun prototype
    @FunctionalInterface
    public interface Callable<V> {
        V call() throws Exception;
    }
    */
    /*
    * Future
    public interface Future<V> {
        boolean cancel(boolean mayInterruptIfRunning);

        boolean isCancelled();

        boolean isDone();

        V get() throws InterruptedException, ExecutionException;

        V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
    }
    */
    /*
    public interface RunnableFuture<V> extends Runnable, Future<V> {
        void run();
    }
    public class FutureTask<V> implements RunnableFuture<V> {

    }*/

}