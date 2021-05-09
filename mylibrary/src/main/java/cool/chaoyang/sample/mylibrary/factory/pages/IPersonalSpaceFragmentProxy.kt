package cool.chaoyang.sample.mylibrary.factory.pages

import cool.chaoyang.sample.mylibrary.factory.FragmentProxy


interface IPersonalSpacePage {
    fun setData(name: String)
}

interface IPersonalSpaceFragmentProxy : FragmentProxy, IPersonalSpacePage