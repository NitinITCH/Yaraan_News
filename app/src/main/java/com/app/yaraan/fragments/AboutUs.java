package com.app.yaraan.fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
public class AboutUs extends Fragment {

    private TextView txtAboutUs;
    SpannableString spannableString;
    Typeface typeface;
    ImageView imgCloseS;
    android.support.v7.widget.Toolbar toolbar;


    public AboutUs() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_us, container, false);
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

        txtAboutUs=getActivity().findViewById(R.id.txtAboutUs);

        spannableString=new SpannableString("ياران په دې باور یوو چې په افغانستان کې رسنۍ د هغه څه په اړه چې د خلکو ژوند ویجاړوي، ډیر خبرونه او راپورونه خپروي، خو د افغانانو په ځانګړې توګه د ځوان نسل لپاره داسې معلومات چې د هغوی پر ورځني ژوند ښې اغیزې وکړي او هغوی سره د هغوی د چارو په سمون کې مرسته وکړي، ډیر کم څه خپریږي.\n" +
                "\n" +
                "موږ باور لرو چې د افغانستان خلک د نړۍ له ګوټ، ګوټ څخه هغو معلوماتو ته د لاس رسي حق لري چې له مخې یې د دوی د عمومي پوهاي کچې لوړیږي، او د دوی د ورځني ژوند په سنبالښت او بهتره کیدو کې مهم رول لوبولی شي.\n" +
                "\n" +
                "موږ باور لرو چې افغانان حق لري  د روغتیا، سپورت، اقتصاد، معاصرې ټیکنالوژۍ، ټولنیز ژوند او شخصي ودې په برخه کې  هیله بښونکي، هڅونکي او لارښوونکي معلومات تر لاسه کړي او همدا د یاران د خوښې برخې دي چې په اړه یې مطالب او لیکنې خپروي.\n" +
                "\n" +
                "زموږ په باور سوله د ولس د وګړو له ذهنونو پیلیږي او که وګړي سوله وغواړي د نړۍ هیڅ ځواک پرې جګړه نه شي تپلی. موږ په همدې موخه هڅه کوو، د خپل ځوان نسل سره مرسته وکړو چې هغوی له ځان سره د یو سوله ایز ژوند تصور تر لاسه او د عملي کیدو لپاره یې کار او هڅې وکړی شي.\n" +
                "\n" +
                "موږ ژمن یوو چې خپلو لوستونکو ته د دوی د روغتیا، هوساینې، اقتصاد او د ورځني ژوند سره د تړلو  مسلو په اړه د نړۍ له بیلابیلو ژبو، پوهانو، متخصصانو او لیکوالو  تازه، کره او باوري معلومات وړاندې کړو.\n" +
                "\n" +
                "د یاران کاري ټیم\n" +
                "\n" +
                "یاران د خپلو ټولو لیکنو لپاره خپل ټاکلی لیکوال او حق الزحمه يي همکاران لري، چې د یو شریک ټیم او ډلې په توګه د خپلو لوستونکو لپاره ګټور او باوري معلومات راټولوي. له همدې کبله موږ هغه لیکنې چې له بیلابیلو ایمیلونو موږ ته رارسیږي، نه خپروو، په ځانګړې توګه هغه لیکنې چې په نورو ویب پاڼو کې خپرې شوې وي. که تاسو غواړئ په یاران کې مو لیکنې خپرې شي نو ددې کار لپاره باید لومړی د یاران چلونکو سره اړیکې ونیسئ او په یو لړ خپرنیزو معیارونو له یو توافق وروسته به په ټاکلي موضوعاتو ستاسو لیکنې خپریږي.\n" +
                "\n" +
                "د یاران د لیکنو  بیاخپراوی\n" +
                "\n" +
                "په یاران کې خپرې شوې لیکنې ټولې د یاران د کاري ټیم تولید او ددې رسنۍ حقوقي ملکیت دی. په انلاین او چاپي توګه ددې لیکنو د بیا خپراوي اجازه یوازې د یاران د چلونکو د توافق نه وروسته ورکول کیږي.\n" +
                "\n" +
                "موږ په دې وروستیو کې ګورو چې یو شمیر ویب پاڼې، مجلې زموږ لیکنې پرته زموږ له توافق خپروي. خو د یو رسنۍ په توګه د رسنیزې امانتدارۍ اخلاق دا ایجابوي چې هره رسنۍ او هره خپرونه خپل تولید ولري او هغوی چې خپله د خپراوي لپاره څه نه لري ورته ضروري نه ده چې خامخا دې یوه ویب پاڼه یا رسنۍ وچلوي. موږ له ټولو ویب پاڼو او رسنیو جدي هیله کوو چې زموږ مطالب دې بیا نه خپروي. د سرغړاوي په صورت کې موږ کولی شو چې په ګوګل او فیسبوک کې د دا ډول ویب پاڼو په اړه رسمي شکایت ثبت کړو چې په نتیجه کې به یې په دې رسنیو کې  پاڼې او لینکونه وتړل شي.\n" +
                "\n" +
                "البته دا شرط یوازې د رسنیو، ویب پاڼو او چاپي کتابونو لپاره دی. زموږ لوستونکي هر ډول چې وغواړي زموږ لیکنې له خپلو ټولنیزو اکاونټونو لکه فیسبوک، ټویټر او ګوګل شریکولی شي او دا کار یې له موږ سره مرسته کوي.\n" +
                "\n" +
                "\n" +
                "\n" +
                "د یاران سره اړیکې\n" +
                "\n" +
                "تاسو هر وخت چې وغواړئ له یاران  سره اړیکې نیولی شئ.\n" +
                "\n" +
                "ایمیل:\n" +
                "\n" +
                "webyaraan@gmail.com\n" +
                "\n" +
                "له دې فیسبوک پاڼې له لارې هم پیغام راستولی شئ.\n" +
                "\n" +
                "https://www.facebook.com/yaraanweb\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n");

//        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 15, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        typeface=Typeface.createFromAsset(getContext().getAssets(), "fonts/NotoSansArabic-SemiBold.ttf");
        txtAboutUs.setText(spannableString);
        txtAboutUs.setTypeface(typeface);

    }
}
