package com.apps.resumebuilderapp.setting;

import java.io.File;
import java.util.ArrayList;

import com.apps.resumebuilderapp.BuildConfig;
import com.apps.resumebuilderapp.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import androidx.fragment.app.ListFragment;
import androidx.core.content.FileProvider;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class BrowseResumeFragment extends ListFragment {

    public static String FILE_PATH = Environment.getExternalStorageDirectory()
            .getPath() + "/Resume builder/";
    private ArrayList<String> mFiles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFiles = new ArrayList<String>();
        File yourDir = new File(FILE_PATH);
        for (File f : yourDir.listFiles()) {
            if (f.isFile())
                if (f.getName().contains(".pdf")
                        || f.getName().contains(".PDF"))
                    mFiles.add(f.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_multiple_choice, mFiles);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        String selectedItem = (String) getListAdapter().getItem(position);
        File file = new File(FILE_PATH + selectedItem);
//		Uri uri = Uri.fromFile(file);
        Uri uri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(uri, "application/pdf");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        getActivity().startActivity(intent);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);
        ListView listView = getListView();
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
                    inflater.inflate(R.menu.menu_item_browse, menu);
                    return true;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode,
                                                   MenuItem menu) {
                    // TODO Auto-generated method stub
                    switch (menu.getItemId()) {
                        case R.id.menu_item_delete_job:
                            @SuppressWarnings("unchecked")
                            ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    String selectedItem = (String) getListAdapter()
                                            .getItem(i);
                                    File file = new File(FILE_PATH + selectedItem);
                                    file.delete();
                                    mFiles.remove(selectedItem);

                                }
                            }
                            mode.finish();
                            adapter.notifyDataSetChanged();
                            return true;
                        case R.id.menu_item_email_job: {

                            @SuppressWarnings("unchecked")
                            ArrayAdapter<String> adapter2 = (ArrayAdapter<String>) getListAdapter();
                            ArrayList<Uri> uris = new ArrayList<Uri>();
                            for (int i = adapter2.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    String selectedItem = (String) getListAdapter()
                                            .getItem(i);
                                    File file = new File(FILE_PATH + selectedItem);
                                    //Uri u = Uri.parse("file://" + FILE_PATH
                                    //		+ selectedItem);
                                    Uri u = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider", file);
                                    uris.add(u);
                                }
                            }
                            Intent aIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                            aIntent.setType("file/pdf");
                            aIntent.putExtra(Intent.EXTRA_SUBJECT, "Resume");
                            aIntent.putParcelableArrayListExtra(
                                    Intent.EXTRA_STREAM, uris);
                            startActivity(aIntent);
                            return true;

                        } // send Emails

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
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                .getMenuInfo();
        int position = info.position;
        @SuppressWarnings("unchecked")
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListView()
                .getAdapter();
        String job = adapter.getItem(position);
        switch (item.getItemId()) {
            case R.id.menu_item_delete_job:
                File file = new File(FILE_PATH + job);
                file.delete();
                mFiles.remove(job);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }// onContextItemSelected

}
