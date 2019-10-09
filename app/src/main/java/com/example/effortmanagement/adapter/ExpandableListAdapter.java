package com.example.effortmanagement.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.effortmanagement.R;
import com.example.effortmanagement.model.dto.ProjectByPMDTO;

import java.util.ArrayList;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<ProjectByPMDTO> listDataHeader; // header titles
    // child data in format of header title, child title
    private List<List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<ProjectByPMDTO> listDataHeader, List<List<String>> _listDataChild) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this._listDataChild = _listDataChild;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(groupPosition);
//                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_view_items, null);
        }


        TextView listViewItem = convertView.findViewById(R.id.lvItem);
        ArrayList<String> item = (ArrayList) getChild(groupPosition,childPosition);
        listViewItem.setText(item.get(childPosition));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ProjectByPMDTO headerTitle = (ProjectByPMDTO) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle.getProjectName());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}