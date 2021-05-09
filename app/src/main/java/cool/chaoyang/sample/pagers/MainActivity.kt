package cool.chaoyang.sample.pagers

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import cool.chaoyang.sample.mylibrary.factory.pages.IPersonalSpacePage
import cool.chaoyang.sample.pagers.behavior.BottomSheetBehavior

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.btnSHow).setOnClickListener {
            BottomSheetBehavior.from(findViewById(R.id.bottomSheet))
                .state = BottomSheetBehavior.STATE_EXPANDED
        }


        val contentRoot = findViewById<ViewGroup>(R.id.contentRoot)


    }
}


class CoupledFragment : Fragment(), IPersonalSpacePage {
    override fun setData(name: String) {

    }
}
