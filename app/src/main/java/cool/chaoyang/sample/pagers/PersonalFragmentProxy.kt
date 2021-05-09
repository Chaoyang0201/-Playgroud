package cool.chaoyang.sample.pagers

import androidx.fragment.app.Fragment
import cool.chaoyang.sample.mylibrary.factory.FragmentProxy
import cool.chaoyang.sample.mylibrary.factory.pages.IPersonalSpacePage
import cool.chaoyang.sample.mylibrary.factory.pages.IPersonalSpaceFragmentProxy

/**
 * @author huangzhaoyang
 */
internal class PersonalFragmentProxyFactory : FragmentProxyFactory<IPersonalSpaceFragmentProxy>() {
    override fun newInstance(): IPersonalSpaceFragmentProxy {
        return PersonalFragmentProxy(CoupledFragment())
    }
}


internal abstract class FragmentProxyFactory<T : FragmentProxy> {
    abstract fun newInstance(): T
}


private class PersonalFragmentProxy(
    private val coupledFragment: CoupledFragment
) : IPersonalSpaceFragmentProxy, IPersonalSpacePage by coupledFragment {
    override val fragment: Fragment get() = coupledFragment
}


