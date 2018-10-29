package com.app.yaraan.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.yaraan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermCondition extends Fragment {

    private TextView txtTermCondition;
    private ImageView imgCloseS;
    Toolbar toolbar;
    SpannableString spannableString;
    Typeface typeface;


    public TermCondition() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_term_condition, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();
    }

    private void init(){


        imgCloseS=getActivity().findViewById(R.id.imgCloseS);

        imgCloseS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        toolbar = getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        txtTermCondition=getActivity().findViewById(R.id.txtTermCondition);

        spannableString=new SpannableString("\n" +
                "\n" +
                "• د یاران د لیکنو  بیاخپراوی :په یاران کې خپرې شوې لیکنې ټولې د یاران د کاري ټیم تولید او ددې رسنۍ حقوقي ملکیت دی. په انلاین او چاپي توګه ددې لیکنو د بیا خپراوي اجازه یوازې د یاران د چلونکو د توافق نه وروسته ورکول کیږي.\n" +
                "\n" +
                "موږ په دې وروستیو کې ګورو چې یو شمیر ویب پاڼې، مجلې زموږ لیکنې پرته زموږ له توافق خپروي. خو د یو رسنۍ په توګه د رسنیزې امانتدارۍ اخلاق دا ایجابوي چې هره رسنۍ او هره خپرونه خپل تولید ولري او هغوی چې خپله د خپراوي لپاره څه نه لري ورته ضروري نه ده چې خامخا دې یوه ویب پاڼه یا رسنۍ وچلوي. موږ له ټولو ویب پاڼو او رسنیو جدي هیله کوو چې زموږ مطالب دې بیا نه خپروي. د سرغړاوي په صورت کې موږ کولی شو چې په ګوګل او فیسبوک کې د دا ډول ویب پاڼو په اړه رسمي شکایت ثبت کړو چې په نتیجه کې به یې په دې رسنیو کې  پاڼې او لینکونه وتړل شي.\n" +
                "\n" +
                "البته دا شرط یوازې د رسنیو، ویب پاڼو او چاپي کتابونو لپاره دی. زموږ لوستونکي هر ډول چې وغواړي زموږ لیکنې له خپلو ټولنیزو اکاونټونو لکه فیسبوک، ټویټر او ګوګل شریکولی شي او دا کار یې له موږ سره مرسته کوي.\n" +
                "\n" +
                "• ياران د کارولو لپاره له تاسو څخه خصوصي معلومات نه غواړي. تاسو پرته له دې چې په ياران کې خپل نوم ، برېښنالیک یا نور شخصي معلومات ورکړئ، زموږ لیکنې او مطالب لوستلی او کتلی شئ.\n" +
                "\n" +
                "• که چیرې تاسو یاران ته لیکنې راستوئ، نو یاران حق لري چې ستاسو په نوم ستاسو د لیکنو د خپراوي یا نه خپراوي په اړه پریکړه وکړي.\n" +
                "\n" +
                "• که چیرې تاسو خپله د یاران په تبصرو کې خپل شخصي معلومات ( لکه خپل بشپړ نوم، ایمیل، برېښنالیک، ټیلفون شمیره او نور...) ولیکئ نو یاران یې د پټ پاتې کیدو تضمین نه کوي او ممکنه ده چې پر پاڼه څرګند او خپاره شي.\n" +
                "\n" +
                "• د ياران ویب پاڼې، موبایل اپلیکشن او د یاران د فیسبوک پاڼې د کارونکو تبصرې سره تړلي دي. داسې که تاسو په فیسبوک کې د یاران پر لیکنو تبصره وکړئ هغه د یاران پر ویب پاڼه هم خپریږي. په ورته توګه که د یاران پر ویب پاڼه کې پر کومه لیکنه تبصره وکړئ، ممکنه ده چې د یاران پر فیسبوک پاڼه خپره شي. نو تاسو چې کله هم د یاران پر مطالبو تبصرې کوئ، باید به دې سره موافق و اوسئ.\n" +
                "\n" +
                "• ستاسو له خوا ياران ته د ایمیل او یا هم د فیسبوک د پیغامونو له لارې راغلي لیکونه، پیغامونه، وړاندیزونه، پوښتنې او پیشنهادونه که د خپراوي لپاره مو نه وي را استولي، یاران له ځان سره خوندي ساتي او نه یې خپروي. موږ ژمن یوو چې ستاسو راکړي شخصي معلومات هم له درییم لوري سره نه شریکوو.\n" +
                "\n" +
                "• ياران پر خپل ویب پاڼه او فیسبوک پاڼه ستاسو د خپرو شویو لیکنو او تبصرو د پرېښودولو او یا لرې کولو حق ځان سره ساتي او هر وخت چې وغواړي ستاسو تبصرې یا لیکنې له پاڼې پاکولی شي. موږ خپلو کارونکو او لیکوالو ته هیڅ ډول تضمین نه ورکوو چې د دوی لیکنې او تبصرې به تل زموږ پر ویب پاڼه پاتې وي. ښه داده چې خپلې لیکنې چې درته مهمې دي خپله ځان سره خوندي وساتئ. پر یاران د خپلو لیکنو د زیرمتون حساب مه کوئ.\n" +
                "\n" +
                "• تاسو چې کله هم ياران ته د خپریدو په اړه کومه لیکنه راستوئ. نو یاران. کام له دې پالیسۍ سره باید موافق و اوسئ چې ستاسو لیکنه تاسو ته له مادي یا نورو امتیازونو پرته خپروو. که تاسو د خپلو لیکنو په بدل کې د مادي یا بل امتیاز غوښتونکي یئ نو ياران ته یې د خپریدو لپاره مه استوئ.\n" +
                "\n" +
                "• د یاران فیسبوک پاڼه، د فیسبوک د انلاین کمونیټۍ د ټاکلو اصولو سره سم چلن کوي.\n" +
                "\n" +
                "تاسو باید یاران. کام ته د هر ډول شخصي معلوماتو له ورکولو مخکې، له پورته یادونو سره موافق اوسئ.\n" +
                "\n" +
                "د ياران چلونکي\n" +
                "webyaraan@gmail.com");

//        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 15, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        typeface=Typeface.createFromAsset(getContext().getAssets(), "fonts/NotoSansArabic-SemiBold.ttf");

        txtTermCondition.setTypeface(typeface);
        txtTermCondition.setText(spannableString);

    }
}
