package ru.yweber.flaskdionysus.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.yweber.flaskdionysus.R
import ru.yweber.flaskdionysus.core.BaseFlowFragment
import ru.yweber.flaskdionysus.core.BaseFragment
import ru.yweber.flaskdionysus.core.notifier.VisibleToolbarNotifier
import ru.yweber.flaskdionysus.di.MainFlowHolder
import ru.yweber.flaskdionysus.di.MainFlowRouter
import ru.yweber.flaskdionysus.di.module.installNestedNavigation
import ru.yweber.flaskdionysus.di.utils.HandleCiceroneNavigate
import ru.yweber.flaskdionysus.system.subscribe
import ru.yweber.flaskdionysus.ui.main.state.MainState
import toothpick.Scope
import toothpick.ktp.binding.module
import toothpick.ktp.delegate.inject

/**
 * Created on 30.03.2020
 * @author YWeber */

class MainFlowFragment : BaseFlowFragment(R.layout.fragment_main_flow) {

    override val viewModel: MainFlowViewModel by inject<MainFlowViewModel>()

    override val navigator: Navigator
        get() = object : SupportAppNavigator(requireActivity(), childFragmentManager, R.id.containerMainFlow) {
            override fun setupFragmentTransaction(
                command: Command,
                currentFragment: Fragment?,
                nextFragment: Fragment?,
                fragmentTransaction: FragmentTransaction
            ) {
                fragmentTransaction.setReorderingAllowed(true)
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun installModule(scope: Scope) {
        scope.installModules(module {
            bind(VisibleToolbarNotifier::class.java).singleton()
        })
        scope.installNestedNavigation<MainFlowRouter, MainFlowHolder>(HandleCiceroneNavigate.MAIN_NAVIGATION)
        scope.installViewModel<MainFlowViewModel>()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(viewModel.state, ::renderState)
        if (childFragmentManager.fragments.isEmpty()) {
            viewModel.navigateToDrinks()
        }
    }

    private fun renderState(state: MainState) {

    }


    override fun backPressed(): Boolean {
        return childFragmentManager.fragments.lastOrNull {
            (it as? BaseFragment)?.backPressed() ?: false
        } is BaseFragment
    }
}