package com.example.lab2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// 1. 【導入】導入 View Binding 所需的類
//    (假設你的 layout 檔案是 activity_main.xml)
import com.example.lab2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 2. 【新增】聲明 View Binding 變數
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 3. 【修改】初始化 View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 4. 【修改】setContentView 改為 binding.root
        setContentView(binding.root)

        // 5. 【修改】使用 binding.main 取代 findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}