package com.rsschool.android2021;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements SecondFragment.BackButtonListener, FirstFragment.GenerateButtonListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openFirstFragment(0);
    }

    private void openFirstFragment(int previousNumber) {
        final Fragment firstFragment = FirstFragment.newInstance(previousNumber);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, firstFragment)
                           .commit();
    }

    private void openSecondFragment(int min, int max) {
        final Fragment firstFragment = SecondFragment.newInstance(min, max);
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, firstFragment)
                    .commit();
    }

    @Override
    public void onBackPressed()
    {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(currentFragment instanceof SecondFragment)
            ((SecondFragment)currentFragment).onBack();
        else
            super.onBackPressed();
    }

    @Override
    public void onBackButtonClicked(int result)
    {
        openFirstFragment(result);
    }

    @Override
    public void onGenerateButtonClicked(int min, int max)
    {
        openSecondFragment(min, max);
    }
}
