package com.baya.contactwathsapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<String> contactList;

    public ContactAdapter(List<String> contacts) {
        this.contactList = contacts;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View view;
        TextView contact_detail;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            view = itemView;
            contact_detail = view.findViewById(R.id.contact_detail);
        }
    }

    public void setContactList(List<String> contacts) {
        this.contactList = contacts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ContactAdapter.ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContactAdapter.ViewHolder holder, int position) {
        final String contact = contactList.get(position);
        holder.contact_detail.setText(contact);
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

}
