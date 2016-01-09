package com.tiny.learning_rxandroid;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String CATEGORY_EXAMPLE = MainActivity.class.getPackage().getName();
    private static final String TAG_CLASS_NAME = "classname";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_INTENT = "intent";
    private ListView mList;

    private static final Comparator<Map<String, Object>> DISPLAY_NAME_COMPARABLE = new Comparator<Map<String, Object>>() {
        private final Collator mCollator = Collator.getInstance();

        @Override
        public int compare(Map<String, Object> lhs, Map<String, Object> rhs) {
            return mCollator.compare(lhs.get("className"), rhs.get("className"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mList = (ListView) findViewById(R.id.method_list);
        mList.setOnItemClickListener(this);
        refreshData();

    }

    private void refreshData() {
        mList.setAdapter(new SimpleAdapter(getApplication(), getData(), R.layout.list_item_layout, new String[]
                {TAG_CLASS_NAME, TAG_DESCRIPTION}, new int[]{R.id.txt_class_name, R.id.txt_description}));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> data = new ArrayList<>();

        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.setPackage(getApplicationContext().getPackageName());
        mainIntent.addCategory(CATEGORY_EXAMPLE);

        PackageManager pm = getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);

        if (list == null) {
            return data;
        }

        for (ResolveInfo info : list) {
            CharSequence labelSep = info.loadLabel(pm);
            String label = labelSep != null ? labelSep.toString() : info.activityInfo.name;

            String[] labelPath = label.split("/");
            for (int i = 0; i < labelPath.length; i++) {
                Log.d(CATEGORY_EXAMPLE, labelPath[i]);
            }

            String nextLabel = labelPath[0];
            if (labelPath.length == 1) {
                String nameLabel = label;
                addItem(data, nameLabel, nextLabel, activityIntent(info.activityInfo.packageName, info.activityInfo.name));
            }

        }
        //Collections.sort(data, DISPLAY_NAME_COMPARABLE);
        return data;
    }

    private Intent activityIntent(String pkg, String componentName) {
        Intent intent = new Intent();
        intent.setClassName(pkg, componentName);
        return intent;
    }

    /**
     * add item date
     *
     * @param data
     * @param className
     * @param description
     * @param intent
     */
    private void addItem(List<Map<String, Object>> data, String className, String description, Intent intent) {
        Map<String, Object> temp = new HashMap<>();
        temp.put(TAG_CLASS_NAME, className);
        temp.put(TAG_DESCRIPTION, description);
        temp.put(TAG_INTENT, intent);
        data.add(temp);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);

        Intent intent = (Intent) map.get(TAG_INTENT);
        startActivity(intent);
    }
}
