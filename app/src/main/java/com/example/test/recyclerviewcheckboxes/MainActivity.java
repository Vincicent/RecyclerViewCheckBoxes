package com.example.test.recyclerviewcheckboxes;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public class SpiritualTeacher {
        private String name,quote;
        private int image;
        private boolean isSelected;

        public SpiritualTeacher(String name, String quote, int image) {
            this.name = name;
            this.quote = quote;
            this.image = image;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getQuote() {
            return quote;
        }
        public int getImage() {
            return image;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView img;
        TextView nameTxt,posTxt;
        CheckBox myCheckBox;

        ItemClickListener itemClickListener;

        public MyHolder(View itemView) {
            super(itemView);

            nameTxt= itemView.findViewById(R.id.nameTextView);
            posTxt= itemView.findViewById(R.id.descritionTextView);
            img= itemView.findViewById(R.id.teacherImageView);
            myCheckBox= itemView.findViewById(R.id.myCheckBox);

            myCheckBox.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener ic)
        {
            this.itemClickListener=ic;
        }
        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }
        interface ItemClickListener {

            void onItemClick(View v,int pos);
        }
    }
    static class MyAdapter extends RecyclerView.Adapter<MyHolder> {

        Context c;
        SpiritualTeacher[] teachers;
        ArrayList<SpiritualTeacher> checkedTeachers=new ArrayList<>();

        public MyAdapter(Context c, SpiritualTeacher[] teachers) {
            this.c = c;
            this.teachers = teachers;
        }

        //VIEWHOLDER IS INITIALIZED
        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.model,null);
            MyHolder holder=new MyHolder(v);
            return holder;
        }

        //DATA IS BOUND TO VIEWS
        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            final SpiritualTeacher teacher=teachers[position];
            holder.nameTxt.setText(teacher.getName());
            holder.posTxt.setText(teacher.getQuote());
            holder.myCheckBox.setChecked(teacher.isSelected());
            holder.img.setImageResource(teacher.getImage());

            holder.setItemClickListener(new MyHolder.ItemClickListener() {
                @Override
                public void onItemClick(View v, int pos) {
                    CheckBox myCheckBox= (CheckBox) v;
                    SpiritualTeacher currentTeacher=teachers[pos];

                    if(myCheckBox.isChecked()) {
                        currentTeacher.setSelected(true);
                        checkedTeachers.add(currentTeacher);
                    }
                    else if(!myCheckBox.isChecked()) {
                        currentTeacher.setSelected(false);
                        checkedTeachers.remove(currentTeacher);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return teachers.length;
        }

    }
    private SpiritualTeacher[] getTeachers() {
        SpiritualTeacher[] spiritualTeachers={
                new SpiritualTeacher("Rumi","Out beyond ideas of wrongdoing and rightdoing there is a field.I'll meet you there.", R.drawable.ico),
                new SpiritualTeacher("Anthony De Mello","Don't Carry Over Experiences from the past",R.drawable.ico),
                new SpiritualTeacher("Eckhart Tolle","Walk as if you are kissing the Earth with your feet.",R.drawable.ico),
                new SpiritualTeacher("Meister Eckhart","Man suffers only because he takes seriously what the gods made for fun.",R.drawable.ico),
                new SpiritualTeacher("Mooji","I have lived with several Zen masters -- all of them cats.",R.drawable.ico),
                new SpiritualTeacher("Confucius","I'm simply saying that there is a way to be sane. I'm saying that you ",R.drawable.ico),
                new SpiritualTeacher("Francis Lucille","The way out is through the door. Why is it that no one will use this method?",R.drawable.ico),
                new SpiritualTeacher("Thich Nhat Hanh","t is the power of the mind to be unconquerable.",R.drawable.ico),
                new SpiritualTeacher("Dalai Lama","It's like you took a bottle of ink and you threw it at a wall. Smash! ",R.drawable.ico),
                new SpiritualTeacher("Jiddu Krishnamurti","A student, filled with emotion and crying, implored, 'Why is there so much suffering?",R.drawable.ico),
                new SpiritualTeacher("Osho","Only the hand that erases can write the true thing.",R.drawable.ico),
                new SpiritualTeacher("Sedata","Many have died; you also will die. The drum of death is being beaten.",R.drawable.ico),
                new SpiritualTeacher("Allan Watts","Where there are humans, You'll find flies,And Buddhas.",R.drawable.ico),
                new SpiritualTeacher("Leo Gura","Silence is the language of Om. We need silence to be able to reach our Self.",R.drawable.ico),
                new SpiritualTeacher("Rupert Spira","One day in my shoes and a day for me in your shoes, the beauty of travel lies ",R.drawable.ico),
                new SpiritualTeacher("Sadhguru","Like vanishing dew,a passing apparition or the sudden flashnof lightning",R.drawable.ico)
        };

        return spiritualTeachers;
    }
    StringBuilder sb=null;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter=new MyAdapter(this,getTeachers());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sb=new StringBuilder();

                int i=0;
                do {
                    SpiritualTeacher spiritualTeacher=adapter.checkedTeachers.get(i);
                    sb.append(spiritualTeacher.getName());
                    if(i != adapter.checkedTeachers.size()-1){
                        sb.append("\n");
                    }
                    i++;

                }while (i < adapter.checkedTeachers.size());

                if(adapter.checkedTeachers.size()>0)
                {
                    Toast.makeText(MainActivity.this,sb.toString(),Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(MainActivity.this,"Please Check An Item First", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //RECYCLER
        RecyclerView rv= (RecyclerView) findViewById(R.id.myRecycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());

        //SET ADAPTER
        rv.setAdapter(adapter);

    }

}
