package Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vivek.android2.coordinatelayoutwithtablayout.MainActivity;
import com.vivek.android2.coordinatelayoutwithtablayout.R;

import java.util.ArrayList;

import Model.Model;

/**
 * Created by android2 on 2/8/17.
 */

public class First extends Fragment {
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        ArrayList<Model> arrModel = new ArrayList<>();

        Model model = new Model();
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);
        model.setName("Vivek");
        arrModel.add(model);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new MyAdapter(arrModel));

       /* recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                Toast.makeText(getActivity(), "" + rv.getId() + "", Toast.LENGTH_SHORT).show();

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/

        return view;
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        ArrayList<Model> arrModel = new ArrayList<>();

        public MyAdapter(ArrayList<Model> arrModel) {
            this.arrModel = arrModel;
            Toast.makeText(getActivity(), "" + this.arrModel.size() + "", Toast.LENGTH_SHORT).show();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public MyViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text_view);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            return new MyViewHolder(view);
        }


        @Override
        public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
            holder.textView.setText(arrModel.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return arrModel.size();
        }
    }
}
