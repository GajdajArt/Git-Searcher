package com.labralab.githubsearcher.views;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.labralab.githubsearcher.App;
import com.labralab.githubsearcher.R;
import com.labralab.githubsearcher.presenters.MainPresenter;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rVCompany;
    private TextInputLayout tILSearch;
    private EditText eTSearch;
    private RecyclerView.LayoutManager layoutManager;
    private TextView hintTV;
    private TextView historyTV;
    private ProgressBar progressBar;



    @Inject
    MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инжектим экземпляр презентера
        App.getAppComponents().inject(this);

        //Подключаемся ко всем view
        initView();


    }

    //Подключаемся ко всем view
    private void initView() {

        //ProgressBar
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        //RecyclerView
        rVCompany = (RecyclerView) findViewById(R.id.company_RV);
        layoutManager = new LinearLayoutManager(this);
        rVCompany.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rVCompany.setLayoutManager(layoutManager);

        //TextInputLayout и TextView
        tILSearch = (TextInputLayout) findViewById(R.id.searchTIL);
        eTSearch = tILSearch.getEditText();
        //Убераем надоедливый автофокус
        eTSearch.setFocusable(false);

        //TextView подсказки
        hintTV = (TextView) findViewById(R.id.hint);
        //TextView истории
        historyTV = (TextView) findViewById(R.id.historyTV);

        //Обработка введения текста в строку поиска
        eTSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            //Поиск начинается после введения 3-го символа
            @Override
            public void afterTextChanged(Editable editable) {

                mainPresenter.showPB();
                if (editable.length() >= 3){
                    mainPresenter.search(editable.toString());
                }
                if(editable.length() < 3){
                    mainPresenter.clearData(editable.toString());
                    mainPresenter.hidePB();
                }
                if(editable.length() == 0){
                    mainPresenter.setStartHint();
                }

            }
        });

        //Обработка касания для возвращения фокуса строке поиска
        eTSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        //Обработка нажатия на футер для открытия HistoryActivity
        //В историю добавляются те организации на которыхсовершался переход к ResultActivity
        historyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPresenter.startHistoryActivity();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Подключаемся к презентеру
        mainPresenter.connect(this);
    }

    public RecyclerView getRVCompany() {
        return rVCompany;
    }

    public EditText geteTSearch() {
        return eTSearch;
    }

    public TextView getHintTV() {
        return hintTV;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }
}
