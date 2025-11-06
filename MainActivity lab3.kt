package com.example.lab3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lab3.databinding.ActivityMainBinding // 匯入自動產生的 Binding Class

class MainActivity : AppCompatActivity() {

    // 宣告一個 binding 變數
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // --- View Binding Step 1: 初始化 ---
        // "ActivityMainBinding" 是 Android Studio 根據你的 "activity_main.xml" 自動產生的
        binding = ActivityMainBinding.inflate(layoutInflater)
        // --- View Binding Step 2: 設定 Content View ---
        setContentView(binding.root) // 改用 binding.root

        // 注意：這裡要用 binding.main (或你 XML 根佈局的 ID)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Step1 (已優化): 元件現在全部在 binding 物件中，不需再 findViewById ---

        // Step2 設定 btnMora 的點擊事件
        binding.btnMora.setOnClickListener {
            // Step3 如果 edName 為空，則顯示提示文字
            if (binding.edName.text.isEmpty()) {
                binding.tvText.text = "請輸入玩家姓名"
                return@setOnClickListener
            }
            
            // Step4 從 edName 取得玩家姓名
            val playerName = binding.edName.text.toString()

            // Step5 優化：使用 Enum Class
            val targetMora = Mora.fromInt((0..2).random()) // 產生 Mora.SCISSOR, .STONE 或 .PAPER

            // Step6 優化：使用 Enum Class
            val myMora = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.btnScissor -> Mora.SCISSOR
                R.id.btnStone -> Mora.STONE
                else -> Mora.PAPER
            }

            // Step8 設定玩家姓名、我方出拳、電腦出拳的文字
            binding.tvName.text = "名字\n$playerName"
            // 優化：呼叫 Enum 上的 helper function
            binding.tvMyMora.text = "我方出拳\n${myMora.toDisplayString()}"
            binding.tvTargetMora.text = "電腦出拳\n${targetMora.toDisplayString()}"

            // Step9 優化：判斷勝負 (可讀性更高)
            when {
                // 狀況一：平手
                myMora == targetMora -> {
                    binding.tvWinner.text = "勝利者\n平手"
                    binding.tvText.text = "平局，請再試一次！"
                }
                
                // 狀況二：玩家贏
                (myMora == Mora.SCISSOR && targetMora == Mora.PAPER) ||
                (myMora == Mora.STONE && targetMora == Mora.SCISSOR) ||
                (myMora == Mora.PAPER && targetMora == Mora.STONE) -> {
                    binding.tvWinner.text = "勝利者\n$playerName"
                    binding.tvText.text = "恭喜你獲勝了！！！"
                }

                // 狀況三：電腦贏 (所有剩餘情況)
                else -> {
                    binding.tvWinner.text = "勝利者\n電腦"
                    binding.tvText.text = "可惜，電腦獲勝了！"
                }
            }
        }
    }

    // Step7 優化：這個函式已經被移到 Mora.kt 裡面，MainActivity 保持乾淨
    // private fun getMoraString(mora: Int): String { ... }
}