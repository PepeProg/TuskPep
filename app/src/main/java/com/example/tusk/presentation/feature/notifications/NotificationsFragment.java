package com.example.tusk.presentation.feature.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.tusk.R;
import com.example.tusk.presentation.MainApplication;
import com.example.tusk.presentation.feature.all_tasks.AllTasksUseCases;
import com.github.terrakok.cicerone.Router;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.mikepenz.fastadapter.drag.ItemTouchCallback;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.jvm.internal.Intrinsics;

public class NotificationsFragment extends Fragment {

    @Inject
    public AllTasksUseCases notificationUseCases;

    @Inject
    public Router router;

    private ImageButton notificationsButton;
    private NotificationsViewModel viewModel;

    private ItemAdapter notificationAdapter = new ItemAdapter();
    private FastAdapter fastAdapter = FastAdapter.with(notificationAdapter);


    private RecyclerView notificationRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NotificationsViewModel.class);

        MainApplication.dagger.inject(this);
    }

    @Nullable
    public View onCreateView(@NotNull LayoutInflater inflater
                            ,@Nullable ViewGroup container
                            ,@Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        notificationsButton = requireActivity().findViewById(R.id.notification_button);
        return inflater.inflate(R.layout.notifications_fragment, container, false);
    }

    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(view, "view");
        super.onViewCreated(view, savedInstanceState);
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        notificationRecycler = getView().findViewById(R.id.notificationRecycler);
        notificationRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        notificationRecycler.setAdapter(fastAdapter);
        observeTasks();
    }

    @Override
    public void onStart() {
        super.onStart();
        notificationsButton.setOnClickListener((View view) -> router.exit());
    }

    private void observeTasks(){
    }

    @NonNull
    public static NotificationsFragment newInstance(){
        return new NotificationsFragment();
    }

}
