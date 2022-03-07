package edu.ucsd.cse110.dogegotchi.FoodMenu;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Collection;

import edu.ucsd.cse110.dogegotchi.R;
import edu.ucsd.cse110.dogegotchi.doge.Doge;
import edu.ucsd.cse110.dogegotchi.doge.Doge.State;
import edu.ucsd.cse110.dogegotchi.doge.IDogeObserver;
import edu.ucsd.cse110.dogegotchi.observer.ISubject;
import edu.ucsd.cse110.dogegotchi.ticker.ITickerObserver;

public class Menu implements ISubject<IMenuObserver>, IDogeObserver, ITickerObserver {

    private Collection<IMenuObserver> observers;
    private int pizzaTime;
    View menu;

    public Menu(View view) {
        menu = view;
        observers = new ArrayList<IMenuObserver>();
        final ImageButton hamButton       = view.findViewById(R.id.HamButton),
                steakButton     = view.findViewById(R.id.SteakButton),
                turkeyLegButton = view.findViewById(R.id.TurkeyLegButton);
        View.OnClickListener bruh = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (IMenuObserver observer : observers) observer.feedDoge();
            }
        };
        hamButton.setOnClickListener(bruh);
        steakButton.setOnClickListener(bruh);
        turkeyLegButton.setOnClickListener(bruh);
    }

    @Override
    public void onStateChange(Doge.State newState) {
        if (newState.equals(State.SAD)){
            menu.setVisibility(View.VISIBLE);
            Log.d(this.getClass().getSimpleName(), "This value should be VISIBLE" + menu.getVisibility());
        }
        else if (newState.equals(State.EATING)) {
            menu.setVisibility(View.INVISIBLE);
            pizzaTime = 5;
        }
    }

    @Override
    public void register(IMenuObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(IMenuObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void onTick() {
        pizzaTime--;
        if (pizzaTime == 0) {
            for (IMenuObserver observer : observers) observer.dogeFed();
        }

    }
}
