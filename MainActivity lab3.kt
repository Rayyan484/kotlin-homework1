package com.example.lab3

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
// 1. 【導入】導入 View Binding 所需的類
import com.example.lab3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 2. 【新增】聲明 View Binding 變數
    private lateinit var binding: ActivityMainBinding

    // 3. 【新增】定義常數，取代 0, 1, 2，提高可讀性
    companion object {
        private const val SCISSOR = 0
        private const val STONE = 1
        private const val PAPER = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 4. 【修改】初始化 View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        // 5. 【修改】setContentView 改為 binding.root
        setContentView(binding.root)

        // 6. 【修改】使用 binding.main 取代 findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 7. 【移除】所有 findViewById (原始 Step 1)，已由 View Binding 取代

        // 8. 【修改】Step 2：使用 binding.btnMora 設置監聽器
        binding.btnMora.setOnClickListener {
            // 9. 【修改】Step 3：使用 binding.edName 和 binding.tvText
            //    【優化】使用 getString(R.string....) 從字串資源獲取文字
            if (binding.edName.text.isEmpty()) {
                binding.tvText.text = getString(R.string.hint_player_name) // "請輸入玩家姓名"
                return@setOnClickListener
            }
            // Step 4
            val playerName = binding.edName.text.toString()
            // Step 5 & 10. 【優化】使用常數
            val targetMora = (SCISSOR..PAPER).random()
            
            // Step 6 & 10. 【優化】使用常數
            val myMora = when (binding.radioGroup.checkedRadioButtonId) {
                R.id.btnScissor -> SCISSOR
                R.id.btnStone -> STONE
                else -> PAPER
            }

            // Step 8 & 11. 【優化】使用格式化的字串資源 (String resources)
            // 避免在 Code 中使用 "\n" 拼接字串
            binding.tvName.text = getString(R.string.label_name, playerName)
            binding.tvMyMora.text = getString(R.string.label_my_mora, getMoraString(myMora))
            binding.tvTargetMora.text = getString(R.string.label_target_mora, getMoraString(targetMora))

            // Step 9 & 12. 【優化】簡化勝利判斷邏輯，並使用字串資源
            when {
                myMora == targetMora -> {
                    // 平手
                    binding.tvWinner.text = getString(R.string.label_winner, getString(R.string.result_draw))
                    binding.tvText.text = getString(R.string.status_draw)
                }
                // (剪刀0+1)%3 = 1(石頭) => 電腦贏
                // (石頭1+1)%3 = 2(布)   => 電腦贏
                // (布  2+1)%3 = 0(剪刀) => 電腦贏
                (myMora + 1) % 3 == targetMora -> {
                    // 電腦贏
                    binding.tvWinner.text = getString(R.string.label_winner, getString(R.string.player_computer))
                    binding.tvText.text = getString(R.string.status_lose)
                }
                else -> {
                    // 玩家贏
                    binding.tvWinner.text = getString(R.string.label_winner, playerName)
                    binding.tvText.text = getString(R.string.status_win)
                }
            }
        }
    }

    // Step 7 & 13. 【優化】使用常數和字串資源
    private fun getMoraString(mora: Int): String {
        return when (mora) {
            SCISSOR -> getString(R.string.mora_scissor) // "剪刀"
            STONE -> getString(R.string.mora_stone) // "石頭"
            else -> getString(R.string.mora_paper) // "布"
        }
    }
}