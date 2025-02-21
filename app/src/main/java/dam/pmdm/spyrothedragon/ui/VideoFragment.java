package dam.pmdm.spyrothedragon.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import dam.pmdm.spyrothedragon.R;
import dam.pmdm.spyrothedragon.databinding.FragmentVideoBinding;


public class VideoFragment extends Fragment {

    private FragmentVideoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       binding = FragmentVideoBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        cargarVideo();


    }

    private void cargarVideo() {
        binding.videoView.setVideoPath("android.resource://" + getActivity().getPackageName() + "/" + R.raw.agua);
        binding.videoView.start();

    }


}