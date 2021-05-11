package cool.chaoyang.sample.pagers

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import cool.chaoyang.sample.mylibrary.factory.pages.IPersonalSpacePage
import cool.chaoyang.sample.pagers.behavior.BottomSheetBehavior
import cool.chaoyang.sample.pagers.behavior.DrawerBehavior

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)


        val findViewById = findViewById<ViewPager2>(R.id.vp)


        findViewById.adapter = PagerAdapter(findViewById,this)
    }
}

class PagerAdapter(private val vp:ViewPager2,activity:FragmentActivity) : FragmentStateAdapter(activity){

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            FragmentA(vp)
        }else{
            FramentB()
        }
    }

}


class FragmentA(private val vp: ViewPager2): Fragment(R.layout.activity_main){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val findViewById = view.findViewById<ViewGroup>(R.id.rightDrawer)
        val from = DrawerBehavior.from(findViewById)

        from.addDrawerCallback(object :
            DrawerBehavior.DrawerCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == DrawerBehavior.STATE_HIDDEN){
                    vp.isUserInputEnabled = false
                }else if(newState == DrawerBehavior.STATE_EXPANDED){
                    vp.isUserInputEnabled = true
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

        from.state = DrawerBehavior.STATE_HIDDEN
        vp.isUserInputEnabled = false
    }
}


class FramentB:Fragment(R.layout.activity_main){

}

class CoupledFragment : Fragment(), IPersonalSpacePage {
    override fun setData(name: String) {

    }
}
