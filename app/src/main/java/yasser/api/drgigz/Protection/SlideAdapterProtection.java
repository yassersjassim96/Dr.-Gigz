package yasser.api.drgigz.Protection;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import yasser.api.drgigz.R;


public class SlideAdapterProtection extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    //list of images
    public int[] lst_images = {
            R.drawable.washing_hands,
            R.drawable.distance,
            R.drawable.dont_touch,
    };
    //list of titles
    public String[] lst_title = {
            "نظف يديك",
            "ابتعد مسافة متر",
            "لا تلمس وجهك",
    };
    //list of description
    public String[] lst_description = {
            "نظف يديك جيداً بانتظام بفركهما بمطهر كحولي لليدين أو بغسلهما بالماء والصابونً",
            "لا تقل عن متر واحد (3 أقدام) بينك وبين أي شخص يسعل أو يعطس",
            "تجنب لمس عينيك وأنفك وفمك",
    };
    //list of background colors
    public int[] lst_backgroundcolor = {
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 255),
            Color.rgb(255, 255, 255),
    };

    public SlideAdapterProtection (Context context){
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
        ImageView imgslide = (ImageView) view.findViewById(R.id.slideimg);
        TextView txttitle = (TextView) view.findViewById(R.id.txttitle);
        TextView description = (TextView) view.findViewById(R.id.txtdescription);
        layoutslide.setBackgroundColor(lst_backgroundcolor[position]);
        imgslide.setImageResource(lst_images[position]);
        txttitle.setText(lst_title[position]);
        description.setText(lst_description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
