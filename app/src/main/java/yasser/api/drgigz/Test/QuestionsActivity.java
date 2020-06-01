package yasser.api.drgigz.Test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.r0adkll.slidr.Slidr;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import yasser.api.drgigz.R;

public class QuestionsActivity extends AppCompatActivity {

    public static final String FILE_NAME = "QUIZZER";
    public static final String KEY_NAME = "QUESTIONS";

    private TextView question, noIndicator;
    private FloatingActionButton bookmarkBtn;
    private LinearLayout optionsContainer;
    private Button shareBtn, nextBtn;
    private int count = 0;
    private List<QuestionModel> list;
    private int position = 0;
    private int score = 0;
    private int setNo;
    private Dialog loadingDialog;

    private List<QuestionModel> bookmarksList;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int matchedQuestionPosition;

    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        Slidr.attach(this);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("اختبر معلوماتك");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        loadAds();

        question = findViewById(R.id.question);
        noIndicator = findViewById(R.id.no_indicator);
        bookmarkBtn = findViewById(R.id.bookmark_btn);
        optionsContainer = findViewById(R.id.options_container);
        shareBtn = findViewById(R.id.share_btn);
        nextBtn = findViewById(R.id.next_btn);

        //get data and its type from intent
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        if (Intent.ACTION_SEND.equals(action) && type != null){
            handleSendText(intent);
        }

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();

        getBookmarks();

        bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelMatch()){
                    bookmarksList.remove(matchedQuestionPosition);
                    bookmarkBtn.setImageResource(R.drawable.bookmark_border);
                } else {
                    bookmarksList.add(list.get(position));
                    bookmarkBtn.setImageResource(R.drawable.bookmark);
                }
            }
        });

        setNo = getIntent().getIntExtra("setNo", 0);

        loadingDialog = new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        //loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corners));
        loadingDialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        list = new ArrayList<>();
        list.add(new QuestionModel("هل جهاز تنشيف اليدين فعال للتخلص من كورونا؟", "فعال", "غير فعال", "فعال عندما تكون يدك مبللة", "غير ذلك", "غير فعال", 1));
        list.add(new QuestionModel("هل يعتبر التعقيم بالأشعة فوق البنفسجية مضراً؟", "نعم مضر، في حال لمس الجلد", "كلا، غير مضر", "مضر إذا كانت يدك مبللة فقط", "غير ذلك", "نعم مضر، في حال لمس الجلد", 2));
        list.add(new QuestionModel("ما هو مصدر فيروس كورونا الحالي COVID-19?", "حساء الخفافيش", "آكل النمل", "الثعابين", "غير معلوم حتى اللحظة", "غير معلوم حتى اللحظة", 3));
        list.add(new QuestionModel("ما هي أفضل طريقة للوقاية من الجائحة؟", "غسل اليدين بانتظام", "الابتعاد عن التجمعات", "تجنب لمس الوجه", "كل ما ذكر", "كل ما ذكر", 4));
        list.add(new QuestionModel("فيروس كورونا يصيب:", "فئة الشباب", "كبار السن", "الأطفال", "جميع الأعمار", "جميع الأعمار", 5));
        list.add(new QuestionModel("تظهر أعراض فيروس كورونا غالباً بعد مرور:", "يوم واحد فقط", "ثلاث أيام", "يوم واحد إلى 14 يوم", "شهر", "يوم واحد إلى 14 يوم", 6));
        list.add(new QuestionModel("يمكن أن يكون فيروس كورونا خطراً على:", "فئة الشباب", "كبار السن والمصابون بالأمراض المزمنة", "المراهقين", "جميع الأعمار", "كبار السن والمصابون بالأمراض المزمنة", 7));
        list.add(new QuestionModel("الطريقة الصحيحة لارتداء الكمامة:", "وضع الجانب الأبيض إلى الخارج في حال أردت حماية نفسك", "وضع الجانب الملون إلى الخارج في حال كنت أنت المريض", "في كل الأحوال يجب ارتدائها وجانبها الملون للخارج", "الجانب الأبيض للخارج دائماً", "في كل الأحوال يجب ارتدائها وجانبها الملون للخارج", 8));

        loadingDialog.show();
        if (list.size() > 0){

            for (int i = 0; i < 4; i++){
                optionsContainer.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkAnswer((Button) v);
                    }
                });
            }
            playAnim(question,0,list.get(position).getQuestion());
            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    nextBtn.setEnabled(false);
                    nextBtn.setAlpha(0.7f);
                    position++;
                    if (position == list.size()){
                        if (interstitialAd.isLoaded()){
                            interstitialAd.show();
                            return;
                        }

                        Intent scoreIntent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                        scoreIntent.putExtra("score",score);
                        scoreIntent.putExtra("total",list.size());
                        startActivity(scoreIntent);
                        finish();
                        return;
                    }
                    count = 0;
                    playAnim(question,0,list.get(position).getQuestion());
                }
            });

            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String body = list.get(position).getQuestion() + "\n" +
                            list.get(position).getOptionA() + "\n" +
                            list.get(position).getOptionB() + "\n" +
                            list.get(position).getOptionC() + "\n" +
                            list.get(position).getOptionD();
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Quizzer challenge");
                    shareIntent.putExtra(Intent.EXTRA_TEXT,body);
                    startActivity(Intent.createChooser(shareIntent,"Share via"));
                }
            });
        } else {
            finish();
            Toast.makeText(QuestionsActivity.this, "no questions", Toast.LENGTH_SHORT).show();
        }
        loadingDialog.dismiss();


    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null){
            question.setText(sharedText);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }


    private void playAnim(final View view, final int value, final String data){

        for (int i = 0; i < 4; i++){
            //optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
            optionsContainer.getChildAt(i).getBackground().setColorFilter(Color.parseColor("#989898"), PorterDuff.Mode.SRC_ATOP);
        }

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (value == 0 && count < 4){
                    String option = "";
                    if (count == 0){
                        option = list.get(position).getOptionA();
                    } else if (count == 1){
                        option = list.get(position).getOptionB();
                    } else if (count == 2){
                        option = list.get(position).getOptionC();
                    } else if (count == 3){
                        option = list.get(position).getOptionD();
                    }
                    playAnim(optionsContainer.getChildAt(count),0,option);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (value == 0){
                    try {
                        ((TextView)view).setText(data);
                        noIndicator.setText(position+1+"/"+list.size());
                        if (modelMatch()){
                            bookmarkBtn.setImageResource(R.drawable.bookmark);
                        } else {
                            bookmarkBtn.setImageResource(R.drawable.bookmark_border);
                        }
                    } catch (ClassCastException ex){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                } else {
                    enableOption(true);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }


    private void checkAnswer(Button selectedOption){
        enableOption(false);
        nextBtn.setEnabled(true);
        nextBtn.setAlpha(1);
        if (selectedOption.getText().toString().equals(list.get(position).getCorrectAns())){
            //correct
            score++;
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP){
                selectedOption.setBackgroundColor(getResources().getColor(R.color.correct));
            } else {
                selectedOption.getBackground().setColorFilter(Color.parseColor("#4caf50"), PorterDuff.Mode.SRC_ATOP);
            }
        } else {
            //incorrect
            selectedOption.getBackground().setColorFilter(Color.parseColor("#ff0000"), PorterDuff.Mode.SRC_ATOP);
            Button correctoption = (Button) optionsContainer.findViewWithTag(list.get(position).getCorrectAns());
            correctoption.getBackground().setColorFilter(Color.parseColor("#4caf50"), PorterDuff.Mode.SRC_ATOP);
        }
    }


    private void enableOption(boolean enable){
        for (int i = 0; i < 4; i++){
            optionsContainer.getChildAt(i).setEnabled(enable);
            if (enable){
                //optionsContainer.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
                optionsContainer.getChildAt(i).getBackground().setColorFilter(Color.parseColor("#989898"), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    private void getBookmarks(){

        String json = preferences.getString(KEY_NAME,"");

        Type type = new TypeToken<List<QuestionModel>>(){}.getType();

        bookmarksList = gson.fromJson(json,type);

        if (bookmarksList == null){
            bookmarksList = new ArrayList<>();
        }
    }

    private boolean modelMatch(){
        boolean matched = false;
        int i = 0;
        for (QuestionModel model : bookmarksList){
            if (model.getQuestion().equals(list.get(position).getQuestion())
            && model.getCorrectAns().equals(list.get(position).getCorrectAns())
            && model.getSetNo() == list.get(position).getSetNo()){
                matched = true;
                matchedQuestionPosition = i;
            }
            i++;
        }
        return matched;
    }

    private void storeBookmarks(){

        String json = gson.toJson(bookmarksList);
        editor.putString(KEY_NAME,json);
        editor.commit();

    }

    private void loadAds(){
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.interstitialAd_id));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                interstitialAd.loadAd(new AdRequest.Builder().build());
                Intent scoreIntent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                scoreIntent.putExtra("score",score);
                scoreIntent.putExtra("total",list.size());
                startActivity(scoreIntent);
                finish();
                return;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
