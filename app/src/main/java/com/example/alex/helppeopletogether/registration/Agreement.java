package com.example.alex.helppeopletogether.registration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.alex.helppeopletogether.R;
import com.example.alex.helppeopletogether.fragmentsFormNavigationDrawer.NewsNavigationDrawer;

/**
 * Created by User on 29.03.2016.
 */
public class Agreement extends Activity implements View.OnClickListener {
    private Intent intent;
    private TextView licenseText;
    private CheckBox licenseCheckBox;
    private Button registration;
    private String text;

    //sdsdsd
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agreement);
        intent = getIntent();
        licenseText = (TextView)findViewById(R.id.agreement_license_text);
        licenseCheckBox = (CheckBox)findViewById(R.id.agreement_license_checkbox);
        registration = (Button)findViewById(R.id.agreement_registration_button);
        text = "ЛИЦЕНЗИОННОЕ СОГЛАШЕНИЕ\n" +
                "На использование технологии «печати по тканым и нетканым текстильным материалам, коже и\n" +
                "кожзаменителям через стандартные принтеры с возможностью дальнейшего использования\n" +
                "принтеров по прямому их назначению - печати по всем бумажным и плёночным носителям,\n" +
                "включая пластик и т.д. без реконструкции принтера и технологической линии», и использования\n" +
                "химических препаратов серии «ФОТОТЕКС» для печати по всем тканым и нетканым\n" +
                "материалам, коже и кожзаменителям, пластику, холстам, фольге, кальке, CD - дискам, а также\n" +
                "создания новых видов товаров, типа фотобумаги, прозрачных и матовых пленок и других товаров\n" +
                "На использование технологии «печати по тканым и нетканым текстильным материалам, коже и\n" +
                "кожзаменителям через стандартные принтеры с возможностью дальнейшего использования\n" +
                "принтеров по прямому их назначению - печати по всем бумажным и плёночным носителям,\n" +
                "включая пластик и т.д. без реконструкции принтера и технологической линии», и использования\n" +
                "химических препаратов серии «ФОТОТЕКС» для печати по всем тканым и нетканым\n" +
                "материалам, коже и кожзаменителям, пластику, холстам, фольге, кальке, CD - дискам, а также\n" +
                "создания новых видов товаров, типа фотобумаги, прозрачных и матовых пленок и других товаров\n" +
                "На использование технологии «печати по тканым и нетканым текстильным материалам, коже и\n" +
                "кожзаменителям через стандартные принтеры с возможностью дальнейшего использования\n" +
                "принтеров по прямому их назначению - печати по всем бумажным и плёночным носителям,\n" +
                "включая пластик и т.д. без реконструкции принтера и технологической линии», и использования\n" +
                "химических препаратов серии «ФОТОТЕКС» для печати по всем тканым и нетканым\n" +
                "материалам, коже и кожзаменителям, пластику, холстам, фольге, кальке, CD - дискам, а также\n" +
                "создания новых видов товаров, типа фотобумаги, прозрачных и матовых пленок и других товаров\n" +
                "На использование технологии «печати по тканым и нетканым текстильным материалам, коже и\n" +
                "кожзаменителям через стандартные принтеры с возможностью дальнейшего использования\n" +
                "принтеров по прямому их назначению - печати по всем бумажным и плёночным носителям,\n" +
                "включая пластик и т.д. без реконструкции принтера и технологической линии», и использования\n" +
                "химических препаратов серии «ФОТОТЕКС» для печати по всем тканым и нетканым\n" +
                "материалам, коже и кожзаменителям, пластику, холстам, фольге, кальке, CD - дискам, а также\n" +
                "создания новых видов товаров, типа фотобумаги, прозрачных и матовых пленок и других товаров\n" +
                "с использованием данных хим. препаратов производства ООО Командор\n"+
                "На использование технологии «печати по тканым и нетканым текстильным материалам, коже и\n" +
                "кожзаменителям через стандартные принтеры с возможностью дальнейшего использования\n" +
                "принтеров по прямому их назначению - печати по всем бумажным и плёночным носителям,\n" +
                "включая пластик и т.д. без реконструкции принтера и технологической линии», и использования\n" +
                "химических препаратов серии «ФОТОТЕКС» для печати по всем тканым и нетканым\n" +
                "материалам, коже и кожзаменителям, пластику, холстам, фольге, кальке, CD - дискам, а также\n" +
                "создания новых видов товаров, типа фотобумаги, прозрачных и матовых пленок и других товаров";

        licenseText.setText(text);
        registration.setEnabled(false);
        registration.setOnClickListener(this);
        licenseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (buttonView.isChecked()){
                    registration.setEnabled(true);
                }else {
                    registration.setEnabled(false);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.agreement_registration_button:
                intent = new Intent(Agreement.this, NewsNavigationDrawer.class);
                startActivity(intent);
                break;
        }
    }
}
