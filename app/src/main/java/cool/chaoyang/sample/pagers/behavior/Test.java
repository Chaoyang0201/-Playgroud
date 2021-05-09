package cool.chaoyang.sample.pagers.behavior;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import cool.chaoyang.sample.mylibrary.factory.pages.IPersonalSpaceFragmentProxy;
import cool.chaoyang.sample.pagers.CoupledFragment;

/**
 * @author huangzhaoyang
 */
class Test {
}


class PersonalFragmentRpoxy2 implements IPersonalSpaceFragmentProxy {

    private final CoupledFragment coupledFragment;

    PersonalFragmentRpoxy2() {
        coupledFragment = new CoupledFragment();
    }

    @NotNull
    @Override
    public Fragment getFragment() {
        return coupledFragment;
    }

    @Override
    public void setData(@NotNull String name) {
        coupledFragment.setData(name);
    }
}