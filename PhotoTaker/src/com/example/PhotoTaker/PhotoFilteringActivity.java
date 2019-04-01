package com.example.PhotoTaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import ru.example.PhotoStream.Camera.Filters.MultiFilter;
import ru.example.PhotoStream.Camera.Filters.TunablePhotoFilter;
import ru.example.PhotoStream.Camera.Filters.TunablePhotoFilterFactory;
import ru.example.PhotoStream.Camera.RawBitmap;

import java.util.concurrent.atomic.AtomicBoolean;

public class PhotoFilteringActivity extends Activity {
    private static final int BRIGHTNESS_PRIORITY = 0;
    private static final int CONTRAST_PRIORITY = 1;
    private static final int LIGHT_REGIONS_PRIORITY = 2;
    private static final int DARK_REGIONS_PRIORITY = 3;
    private static final int SATURATION_PRIORITY = 4;
    private static final int PHOTO_FILTER_PRIORITY = 5;

    private static final int SCALE_DOWN = 4;

    public static Bitmap image = null;

    private Context context;
    private Bitmap currentBitmap, nextBitmap;
    private RawBitmap source, destination;
    private ImageView imageView;
    private Spinner photoFilterSpinner;
    private TunablePhotoFilter brightness, contrast, saturation, lightRegions, darkRegions, photoFilter;
    private SeekBar brightnessBar, contrastBar, saturationBar, lightRegionsBar, darkRegionsBar, photoFilterBar;
    private MultiFilter multiFilter;

    private AtomicBoolean continueRefreshing = new AtomicBoolean(false);
    private AtomicBoolean taskIsRunning = new AtomicBoolean(false);

    private class ImageRefreshTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (continueRefreshing.get()) {
                continueRefreshing.set(false);
                multiFilter.transformOpaqueRaw(source, destination);
                destination.fillBitmap(nextBitmap);
                publishProgress();
            }
            taskIsRunning.set(false);
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... v) {
            imageView.setImageBitmap(nextBitmap);
            Bitmap tmp = currentBitmap;
            currentBitmap = nextBitmap;
            nextBitmap = tmp;
        }
    }

    private void refreshImage() {
        continueRefreshing.set(true);
        if (taskIsRunning.compareAndSet(false, true)) {
            new ImageRefreshTask().execute();
        }
    }

    private class MySeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        private TunablePhotoFilter filter;

        public MySeekBarChangeListener(TunablePhotoFilter filter) {
            this.filter = filter;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            double strength = (progress - seekBar.getMax() / 2) * 2.0 / seekBar.getMax();
            filter.setStrength(strength);
            refreshImage();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            double strength = (seekBar.getProgress() - seekBar.getMax() / 2) * 2.0 / seekBar.getMax();
            filter.setStrength(strength);
            refreshImage();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photofilteringactivity);
        context = this;
        imageView = (ImageView) findViewById(R.id.photofilteringactivity_imageView);
        currentBitmap = Bitmap.createScaledBitmap(image, image.getWidth() / SCALE_DOWN, image.getHeight() / SCALE_DOWN, false);
        nextBitmap = Bitmap.createScaledBitmap(image, image.getWidth() / SCALE_DOWN, image.getHeight() / SCALE_DOWN, false);
        source = new RawBitmap(currentBitmap);
        destination = new RawBitmap(nextBitmap);
        imageView.setImageBitmap(currentBitmap);
        brightness = TunablePhotoFilterFactory.Brightness();
        contrast = TunablePhotoFilterFactory.Contrast();
        saturation = TunablePhotoFilterFactory.Saturation();
        lightRegions = TunablePhotoFilterFactory.LightRegions();
        darkRegions = TunablePhotoFilterFactory.DarkRegions();
        photoFilter = TunablePhotoFilterFactory.NoFilter();
        photoFilterBar = (SeekBar) findViewById(R.id.photofilteringactivity_filterbar);
        photoFilterSpinner = (Spinner) findViewById(R.id.photofilteringactivity_filterspinner);
        TunablePhotoFilterFactory.FilterType[] filterTypes = TunablePhotoFilterFactory.FilterType.values();
        final String[] filterNames = new String[filterTypes.length];
        for (int i = 0; i < filterTypes.length; i++) {
            filterNames[i] = filterTypes[i].toString(context);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, filterNames);
        photoFilterSpinner.setAdapter(adapter);
        photoFilterSpinner.setSelection(0);
        photoFilterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TunablePhotoFilter filter = TunablePhotoFilterFactory.getFilterByName(context, filterNames[position]);
                double strength = (photoFilterBar.getProgress() - photoFilterBar.getMax() / 2) * 2.0 / photoFilterBar.getMax();
                filter.setStrength(strength);
                photoFilterBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(filter));
                multiFilter.changeFilter(PHOTO_FILTER_PRIORITY, filter);
                refreshImage();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        brightnessBar = (SeekBar) findViewById(R.id.photofilteringactivity_brightnessbar);
        contrastBar = (SeekBar) findViewById(R.id.photofilteringactivity_contrastbar);
        saturationBar = (SeekBar) findViewById(R.id.photofilteringactivity_saturationbar);
        lightRegionsBar = (SeekBar) findViewById(R.id.photofilteringactivity_lightregionsbar);
        darkRegionsBar = (SeekBar) findViewById(R.id.photofilteringactivity_darkregionsbar);
        photoFilterBar = (SeekBar) findViewById(R.id.photofilteringactivity_filterbar);
        multiFilter = new MultiFilter();
        multiFilter.changeFilter(SATURATION_PRIORITY, saturation);
        multiFilter.changeFilter(CONTRAST_PRIORITY, contrast);
        multiFilter.changeFilter(BRIGHTNESS_PRIORITY, brightness);
        multiFilter.changeFilter(LIGHT_REGIONS_PRIORITY, lightRegions);
        multiFilter.changeFilter(DARK_REGIONS_PRIORITY, darkRegions);
        multiFilter.changeFilter(PHOTO_FILTER_PRIORITY, photoFilter);
        brightnessBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(brightness));
        contrastBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(contrast));
        saturationBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(saturation));
        lightRegionsBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(lightRegions));
        darkRegionsBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(darkRegions));
        photoFilterBar.setOnSeekBarChangeListener(new MySeekBarChangeListener(photoFilter));
    }
}