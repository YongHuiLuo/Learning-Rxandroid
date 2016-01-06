package com.tiny.learning_rxandroid;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {

    private View rootView;
    private ListView method_list;

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return rootView = inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findView();
        initView();
    }

    private void findView() {
        method_list = (ListView) rootView.findViewById(R.id.method_list);
    }

    private void initView() {
    }
}
