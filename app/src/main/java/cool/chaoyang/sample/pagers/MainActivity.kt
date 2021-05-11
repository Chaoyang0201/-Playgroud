package cool.chaoyang.sample.pagers

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import cool.chaoyang.sample.mylibrary.factory.pages.IPersonalSpacePage
import cool.chaoyang.sample.pagers.behavior.DrawerBehavior

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)


        val findViewById = findViewById<ViewPager>(R.id.vp)

        findViewById.adapter = PagerAdapter(this)
    }
}

class PagerAdapter(activity:FragmentActivity) : FragmentStatePagerAdapter(activity.supportFragmentManager){


    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0){
            FragmentA()
        }else{
            FramentB()
        }
    }

}


class FragmentA : Fragment(R.layout.activity_main){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val findViewById = view.findViewById<ViewGroup>(R.id.rightDrawer)
        val from = DrawerBehavior.from(findViewById)

        from.addDrawerCallback(object :
            DrawerBehavior.DrawerCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                if (newState == DrawerBehavior.STATE_HIDDEN){
//                    vp.isUserInputEnabled = false
//                }else if(newState == DrawerBehavior.STATE_EXPANDED){
//                    vp.isUserInputEnabled = true
//                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }

        })

        from.state = DrawerBehavior.STATE_HIDDEN
    }
}


class FramentB:Fragment(R.layout.activity_pager){

}

class CoupledFragment : Fragment(), IPersonalSpacePage {
    override fun setData(name: String) {

    }
}
