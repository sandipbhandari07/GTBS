package clz.project.gtbs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context mcontext;
    List<IntroScreenItem> mlistcreen;

    public IntroViewPagerAdapter(Context mcontext, List<IntroScreenItem> mlistcreen) {
        this.mcontext = mcontext;
        this.mlistcreen = mlistcreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater=(LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.introlayout_screen,null);

        ImageView imgSlide = layoutScreen.findViewById(R.id.intro_img);
        TextView title = layoutScreen.findViewById(R.id.intro_title);
        TextView description= layoutScreen.findViewById(R.id.intro_description);

        title.setText(mlistcreen.get(position).getTitle());
        description.setText(mlistcreen.get(position).getDescription());
        imgSlide.setImageResource(mlistcreen.get(position).getScreenImg());

        container.addView(layoutScreen);

        return layoutScreen;






    }

    @Override
    public int getCount() {
        return mlistcreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView((View)object);
    }
}
