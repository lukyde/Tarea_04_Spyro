package dam.pmdm.spyrothedragon.adapters;

import static androidx.databinding.DataBindingUtil.setContentView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import dam.pmdm.spyrothedragon.MainActivity;
import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.models.Character;
import dam.pmdm.spyrothedragon.ui.DrawView;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Character> list;
    private Context context;
    private FrameLayout fireContainer;



    public CharactersAdapter(List<Character> charactersList, Context context) {
        this.list = charactersList;
        this.context = context;
    }

    @Override
    public CharactersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        return new CharactersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CharactersViewHolder holder, int position) {
        Character character = list.get(position);
        holder.nameTextView.setText(character.getName());

        // Cargar la imagen (simulado con un recurso drawable)
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(character.getImage(), "drawable", holder.itemView.getContext().getPackageName());
        holder.imageImageView.setImageResource(imageResId);


       holder.itemView.setOnLongClickListener(view -> onClick(view, character.getName(),  holder.fireContainer));

    }

    private boolean onClick(View view, String name, FrameLayout fireContainer) {

    if (name.equals("Spyro")) {

        DrawView drawView = new DrawView( context);;
        fireContainer.addView(drawView);
        fireContainer.bringToFront();
        fireContainer.setVisibility(View.VISIBLE);

        ObjectAnimator animator = ObjectAnimator.ofFloat(drawView, "translationY", 0f, 500f);
        animator.setDuration(2000);
        animator.start();


    }


        return true;
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView imageImageView;
        FrameLayout fireContainer;
        public CharactersViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageImageView = itemView.findViewById(R.id.image);
            fireContainer = itemView.findViewById(R.id.fire_animation_container);
        }
    }
}
