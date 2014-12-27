package com.theme.fragment;

import com.theme.R;
import com.theme.imageLoader.ImageLoader;
import com.theme.utils.PicassoEx;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class FirstFragment extends Fragment {

	private ImageView mimgView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View root = inflater.inflate(R.layout.fragment, container, false);

		mimgView = (ImageView) root.findViewById(R.id.ivFragFirst);
		// tv.setText(getArguments().getString("url"));
		ImageLoader imageLoader = new ImageLoader(getActivity());
		imageLoader.DisplayImg(getArguments().getString("url"), mimgView);
		//picassoLoad(getArguments().getString("url"), mimgView);
		return root;
	}

	public static FirstFragment newInstance(String text) {

		FirstFragment f = new FirstFragment();
		Bundle b = new Bundle();
		b.putString("url", text);

		f.setArguments(b);

		return f;
	}

	/**
	 * set image
	 */
	public void picassoLoad(String url, ImageView imageView) {
		PicassoEx.getPicasso(getActivity()).load(url)
				.error(R.drawable.ic_launcher)
				.placeholder(R.drawable.ic_launcher).fit().into(imageView);
	}
}
