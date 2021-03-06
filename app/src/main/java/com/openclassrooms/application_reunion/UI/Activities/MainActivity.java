package com.openclassrooms.application_reunion.UI.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.application_reunion.DI.DI;
import com.openclassrooms.application_reunion.R;
import com.openclassrooms.application_reunion.UI.Adapters.ReunionRecyclerViewAdapter;
import com.openclassrooms.application_reunion.UI.Fragments.AddReunionFragment;
import com.openclassrooms.application_reunion.UI.Fragments.FilterReunionFragment;
import com.openclassrooms.application_reunion.databinding.ActivityMainBinding;
import com.openclassrooms.application_reunion.model.Reunion;
import com.openclassrooms.application_reunion.service.ReunionApiService;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AddReunionFragment.AddreunionDialogListener, FilterReunionFragment.FilterReunionDialogListener {

    private ReunionApiService mApiService;
    private List<Reunion> mReunions;
    private RecyclerView mRecyclerView;
    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mApiService = DI.getReunionApiService();
        mainBinding = ActivityMainBinding.inflate( getLayoutInflater());
        setContentView(mainBinding.getRoot());

        mRecyclerView = mainBinding.listReunions;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        initList();
        mainBinding.topAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.filtrer) {
                    FilterReunionDialog();
                    return true;
                }
                return false;
            }
        });
        mainBinding.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReunionDialog();
            }
        });



    }
    public void addReunionDialog() {
        DialogFragment newFragment = new AddReunionFragment();
        newFragment.show(getSupportFragmentManager(), "AddReunionFragment");
    }
    public void FilterReunionDialog() {
        DialogFragment newFragment = new FilterReunionFragment();
        newFragment.show(getSupportFragmentManager(), "FilterReunionFragment");
    }

    private void initList() {
        mReunions = mApiService.getReunions();
        mRecyclerView.setAdapter(new ReunionRecyclerViewAdapter(mReunions));
    }
    private void initFilteredList() {
        mReunions = mApiService.getFilteredReunions();
        mRecyclerView.setAdapter(new ReunionRecyclerViewAdapter(mReunions));
    }


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        initList();
    }

    @Override
    public void onFilterDialogPositiveClick(DialogFragment dialog) {initFilteredList(); }
}