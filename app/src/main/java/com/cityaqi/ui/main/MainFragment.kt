package com.cityaqi.ui.main

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cityaqi.R
import com.cityaqi.database.DatabaseHelper
import com.cityaqi.models.CityAqiData
import com.cityaqi.models.DataUpdate
import com.cityaqi.service.WebSocketJobIntentService
import com.cityaqi.tableview.TableViewAdapter
import com.cityaqi.tableview.TableViewListener
import com.cityaqi.tableview.TableViewModel
import com.evrencoskun.tableview.TableView
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }
    val tableViewModel = TableViewModel()
    lateinit var tableViewAdapter :TableViewAdapter;

    private lateinit var viewModel: MainViewModel

    private lateinit var mTableView : TableView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var rootView = inflater.inflate(R.layout.main_fragment, container, false)
        mTableView  = rootView.findViewById(R.id.tableview)
        initializeTableView()
        EventBus.getDefault().register(this);
        return rootView;
    }

    override fun onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        startServiceView()
    }


    fun startServiceView() {
        requireActivity().startService(Intent(activity, WebSocketJobIntentService::class.java))
    }

    fun stopServiceView() {
       requireActivity(). stopService(Intent(activity, WebSocketJobIntentService::class.java))
    }

    private fun initializeTableView() {
        // Create TableView View model class  to group view models of TableView


        // Create TableView Adapter
            tableViewAdapter = TableViewAdapter(tableViewModel)
            mTableView.setAdapter(tableViewAdapter)
            mTableView.setTableViewListener(TableViewListener(mTableView))

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        var cityAQl  = DatabaseHelper.getIntance(activity).cityAQi;
        tableViewAdapter.setAllItems(
            tableViewModel.getColumnHeaderList(), tableViewModel
                .getRowHeaderList(cityAQl), tableViewModel.getCellList(cityAQl)
        )

        //mTableView.setHasFixedWidth(true);

        /*for (int i = 0; i < mTableViewModel.getCellList().size(); i++) {
            mTableView.setColumnWidth(i, 200);
        }*)

        //mTableView.setColumnWidth(0, -2);
        //mTableView.setColumnWidth(1, -2);

        / *mTableView.setColumnWidth(2, 200);
        mTableView.setColumnWidth(3, 300);
        mTableView.setColumnWidth(4, 400);
        mTableView.setColumnWidth(5, 500);*/
    }


    private fun updateTableView() {
        // Create TableView View model class  to group view models of TableView


        // Create TableView Adapter

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        var cityAQl  = DatabaseHelper.getIntance(activity).cityAQi;
        tableViewAdapter.setAllItems(
            tableViewModel.getColumnHeaderList(), tableViewModel
                .getRowHeaderList(cityAQl), tableViewModel.getCellList(cityAQl)
        )
        tableViewAdapter.notifyDataSetChanged()

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDataUpdate(dataUpdate: DataUpdate?) {
        updateTableView()
        println("Subscribe ")
    }
}