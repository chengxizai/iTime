package com.example.itime.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.itime.AddActivity;
import com.example.itime.R;
import com.example.itime.TimeActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FloatingActionButton fab;
    private ListView lv;
    private List<Cat> cats = new ArrayList<>();
    CatAdapter adapter;
    CatSaver catSaver;

    @Override
    public void onDestroy() {
        super.onDestroy();
        catSaver.save();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //序列化
        catSaver=new CatSaver(getActivity());
        cats= catSaver.load();
        if(cats.size()==0);

        adapter = new CatAdapter(getActivity(), R.layout.cat_item, cats);
        ((ListView) root.findViewById(R.id.list_home)).setAdapter(adapter);
        lv = root.findViewById(R.id.list_home);
        fab = root.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), AddActivity.class);
                startActivityForResult(intent,901);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cat book = cats.get(i);
                Intent intent=new Intent(getActivity(), TimeActivity.class);
                String id=String.valueOf(i);
                intent.putExtra("delete_position",id);
                startActivityForResult(intent,902);
            }
        });
        this.registerForContextMenu(lv);
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 901:
                if (resultCode == 1) {
                    int position = data.getIntExtra("edit_position", 0);
                    String name = data.getStringExtra("name");
                    cats.add(position, new Cat(name, R.drawable.a3));
                    adapter.notifyDataSetChanged();
                }
                break;
            case 902:
                if (resultCode == 2) {
                    String str=data.getStringExtra("position");
                    int position=Integer.parseInt(str);
                    cats.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}