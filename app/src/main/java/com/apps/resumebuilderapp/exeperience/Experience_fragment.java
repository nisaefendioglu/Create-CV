package com.apps.resumebuilderapp.exeperience;

import java.util.ArrayList;

import com.apps.resumebuilderapp.R;
import com.apps.resumebuilderapp.exeperience.model.ExperienceData;
import com.apps.resumebuilderapp.exeperience.model.ExperienceDataList;
import com.apps.resumebuilderapp.utils.SessionManager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class Experience_fragment extends ListFragment {
	ArrayList<ExperienceData> mExperienceDataList;

	public static final String Experience_Ui_activity_ID = "com.apps.resumebuilderapp.exeperience.getID";
	ListView listView;
	private SessionManager sessionManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		// mExperienceDataList = ExperienceDataList.getInstance(
		// getActivity()).getPendingJobs();
		mExperienceDataList = ExperienceDataList.getInstance(getActivity()).getPendingJobs();
		ArrayAdapter<ExperienceData> adapter = new ArrayAdapter<ExperienceData>(
				getActivity(),
				android.R.layout.simple_list_item_multiple_choice,
				mExperienceDataList);
		setListAdapter(adapter);
	}

	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.add_menu_fragment, menu);
	}// onCreateOptionsMenu

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return super.onCreateView(inflater, container, savedInstanceState);

	}// onCreateView

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_new_job:
			ExperienceData job = new ExperienceData();
			ExperienceDataList.getInstance(getActivity()).addPendingJob(job);
			Intent i = new Intent(getActivity(), Experience_Ui_activity.class);
			i.putExtra(Experience_Ui_activity_ID, job.getMid());
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}// onOptionsItemSelected finished

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		sessionManager = new SessionManager(getActivity());

		listView = getListView();
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
			registerForContextMenu(listView);
		else {
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			// listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
			listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

				@Override
				public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void onDestroyActionMode(ActionMode arg0) {
					// TODO Auto-generated method stub
				}

				@Override
				public boolean onCreateActionMode(ActionMode mode, Menu menu) {
					// TODO Auto-generated method stub
					MenuInflater inflater = mode.getMenuInflater();
					inflater.inflate(R.menu.menu_item_del, menu);
					return true;
				}

				@Override
				public boolean onActionItemClicked(ActionMode mode,
						MenuItem menu) {
					// TODO Auto-generated method stub
					switch (menu.getItemId()) {
					case R.id.menu_item_delete_job:
						@SuppressWarnings("unchecked")
						ArrayAdapter<ExperienceData> adapter = (ArrayAdapter<ExperienceData>) getListAdapter();
						ExperienceDataList professionalList = ExperienceDataList
								.getInstance(getActivity());
						for (int i = adapter.getCount() - 1; i >= 0; i--) {
							if (getListView().isItemChecked(i)) {
								professionalList.deletePendingJob(adapter
										.getItem(i));
							}
						}
						mode.finish();
						adapter.notifyDataSetChanged();
						return true;
					}
					return false;
				}

				@Override
				public void onItemCheckedStateChanged(ActionMode mode,
						int position, long id, boolean checked) {
					// TODO Auto-generated method stub

				}
			});// AbsListView.MultiChoiceModeListener()
		}// else
	}// oncreat View

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		ExperienceData selectedJob = mExperienceDataList.get(position);
//		Toast.makeText(getActivity(), selectedJob.getmCompanyName(),
//				Toast.LENGTH_SHORT).show();
		Intent i = new Intent(getActivity(), Experience_Ui_activity.class);
		i.putExtra(Experience_Ui_activity_ID, selectedJob.getMid());
		startActivity(i);
		/*
		 * Intent i = new Intent(getActivity(),
		 * Professional_Summary_Ui_Activity.class);
		 * i.putExtra(ProfeesionalSummary_ID, selectedJob.getmId());
		 * startActivity(i);
		 */

	}// onListItemClick

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		ExperienceDataList.getInstance(getActivity()).saveData();
	}// onPause

	@SuppressWarnings("unchecked")
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		((ArrayAdapter<ExperienceData>) getListAdapter())
				.notifyDataSetChanged();
//		setCustomTheme();
	}// onResume

	private void setCustomTheme() {
		listView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

		switch (sessionManager.getAppTheme()) {

          /*public static String custom_theme1 = "Blue";
            public static String custom_theme2 = "Purple";
            public static String custom_theme3 = "Orange";
            public static String custom_theme4 = "Brown";
            public static String custom_theme5 = "Sky"; // Default theme
            public static String custom_theme6 = "Gray";
            public static String custom_theme7 = "Green";
            public static String custom_theme8 = "Teal";
            public static String custom_theme9 = "Red";
            public static String custom_theme10 = "Indigo";*/

			case "Blue": //bkg1
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg1));
				break;

			case "Purple": //bkg2
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg2));
				break;

			case "Orange": //bkg3
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg3));
				break;

			case "Brown": //bkg4
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg4));
				break;

			case "Default":
				listView.setBackgroundDrawable(null);
				listView.setBackgroundColor(getResources().getColor(R.color.white));
				break;

			case "Gray": //bkg5
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg5));
				break;

			case "Green": //bkg6
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg6));
				break;

			case "Teal": //bkg7
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg7));
				break;

			case "Red": //bkg8
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg8));
				break;

			case "Indigo": //bkg9
				listView.setBackgroundDrawable(getResources().getDrawable(R.drawable.bkg9));
				break;
		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		int position = info.position;
		@SuppressWarnings("unchecked")
		ArrayAdapter<ExperienceData> adapter = (ArrayAdapter<ExperienceData>) getListView()
				.getAdapter();
		ExperienceData job = adapter.getItem(position);
		switch (item.getItemId()) {
		case R.id.menu_item_delete_job:
			ExperienceDataList.getInstance(getActivity()).deletePendingJob(job);
			adapter.notifyDataSetChanged();
			return true;
		}
		return super.onContextItemSelected(item);
	}// onContextItemSelected

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		// super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = getActivity().getMenuInflater();
		inflater.inflate(R.menu.menu_item_del, menu);

	}// onCreateContextMenu
}// class end
