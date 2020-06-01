package yasser.api.drgigz.Fake;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import yasser.api.drgigz.R;


public class SlideAdapterFake extends PagerAdapter {

    Context context;
    LayoutInflater inflater;

    //list of drawable
    public int[] lst_img = {
            R.drawable.shopping,
            R.drawable.gloves,
            R.drawable.heat,
            R.drawable.alcohol,
            R.drawable.water,
            R.drawable.chlorine
    };

    //list of titles
    public String[] lst_title = {
            "لا يمكن أن ينتقل فيروس كورونا الجديد (كوفيد-19) عبر السلع",
            "هل ارتداء قفازات مطاطية في الأماكن العامة فعَّالاً في الوقاية من العدوى بفيروس كورونا الجديد؟",
            "هل يستطيع فيروس كورونا الجديد (كوفيد-19) أن يعيش في المناخ الحار والرطب؟",
            "هل تقي المشروبات الكحولية من مرض فيروس كورونا؟",
            "هل شرب الماء يقي من العدوى بمرض فيروس كورونا؟",
            "هل يساعد رش الجسم بالكحول أو الكلور في القضاء على مرض فيروس كورونا؟"
    };
    //list of description
    public String[] lst_description = {
            "رغم أن الفيروس الجديد يمكنه البقاء فوق الأسطح لبضع ساعات أو حتى عدة أيام (حسب نوع السطح)، فمن غير المرجح بقاء الفيروس فوق سطحٍ ما بعد نقله وتغيُّر مكانه وتعرضه لظروف ودرجات حرارة مختلفة" ,
            "لا. المواظبة على غسل اليدين توفر حماية من الإصابة بمرض كوفيد-19 أكثر من ارتداء قفازات مطاطية",
            "نعم، لقد انتشر الفيروس الجديد بالفعل في بلدان ذات مناخ حار ورطب، وفي بلدان أخرى ذات مناخ بارد وجاف",
            "كلا، فتناول المشروبات الكحولية لا يقي  من العدوى بمرض كوفيد-19",
            "من المهم شرب الماء للحفاظ على مستوى الرطوبة في الجسم مما يحفظ الصحة العامة، ولكن لا يقي شرب الماء من العدوى بمرض كوفيد-19",
            "كلا، لن يقضي رش الجسم بالكحول أو الكلور على الفيروسات التي دخلت جسمك بالفعل. بل سيكون ضارًا بالملابس أو الأغشية المخاطية (أي العينين والفم). ومع ذلك، قد يكون الكحول والكلور مفيدين في تعقيم الأسطح، ولكن ينبغي استخدامهما وفقًا للتوصيات الملائمة."
    };
    //list of background colors
    public int[] lst_backgroundcolor = {
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 255),
    };

    public SlideAdapterFake(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return lst_title.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view== (LinearLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_protection,container,false);
        LinearLayout layoutslide = (LinearLayout) view.findViewById(R.id.slidelinearlayout);
        ImageView img = (ImageView) view.findViewById(R.id.slideimg);
        TextView txttitle = (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        img.setImageResource(lst_img[position]);
        txttitle.setText(lst_title[position]);
        txttitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f);
        view.setPadding(150, 150, 150, 150);
        txttitle.setGravity(Gravity.CENTER);
        description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
