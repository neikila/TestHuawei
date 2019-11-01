package ru.neikila.testaddkotlin

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.huawei.hms.aaid.HmsInstanceId



object Dispatchers {
    val NETWORK = newSingleThreadContext("Db")
}

// https://developer.huawei.com/consumer/en/service/hms/catalog/huaweipush_v3.html?page=hmssdk_huaweipush_devprepare_v3

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + kotlinx.coroutines.Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        job = Job()

        launch {
            Log.d("testtest", "started")
            val a = withContext(Dispatchers.NETWORK) {
                Thread.sleep(2000)
                Log.d("testtest", "in process in " + Thread.currentThread().name)
                "result " + Thread.currentThread().name + " "
            }
            Log.d("testtest", "returned")
            Log.d("testtest", "task result " + a + Thread.currentThread().name)
            val result = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
                .adapter(Test::class.java).fromJson("{\"a\":\"qwe\", \"b\": 10}")!!
            Log.d("testtest", "Parsed result $result")
        }
        Log.d("testtest", "activity " + Thread.currentThread().name)
    }

    private fun init() {
        launch {
            val inst = HmsInstanceId.getInstance(this@MainActivity)
            val getToken = inst.getToken("", "HCM")
            if (getToken.isNotEmpty()) {
//                sendRegistrationToServer(getToken);
            }
        }
    }
}

data class Test(val a: String, val b: Int)
