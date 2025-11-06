package com.example.lab2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// 1. 匯入自動產生的 View Binding Class
import com.example.lab2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 2. 宣告一個 binding 變數 (lateinit)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 3. 初始化 binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        
        // 4. 設定 content view 為 binding.root
        setContentView(binding.root)

        // 5. 【優化點】
        // 將 findViewById(R.id.main) 改為 binding.main
        // 這樣更安全、更快速
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}