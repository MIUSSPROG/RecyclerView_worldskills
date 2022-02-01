package com.example.recyclerview_worldskills.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview_worldskills.R
import com.example.recyclerview_worldskills.databinding.ItemUserBinding
import com.example.recyclerview_worldskills.model.User

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
    set(newValue) {
        field = newValue
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        val context = holder.itemView.context
        with(holder.binding){
            holder.itemView.tag = user
            imgvMore.tag = user

            tvUserName.text = user.name
            tvCompany.text = if (user.company.isNotBlank()) user.company else context.getString(R.string.Unemployed)
            if (user.photo.isNotBlank()) {
                Glide.with(imgvPhoto.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .error(R.drawable.ic_baseline_account_circle_24)
                    .into(imgvPhoto)
            }
            else{
                imgvPhoto.setImageResource(R.drawable.ic_baseline_account_circle_24)
            }
        }

    }

    override fun getItemCount(): Int = users.size

    class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val user = v.tag as User
        when(v.id){
            R.id.imgvMore -> {
                showPopupMenu(v)
            }
        }
    }

    private fun showPopupMenu(v: View) {
        val context = v.context
        val popupMenu = PopupMenu(context, v)
        val user = v.tag as User
        val position = users.indexOfFirst { it.id == user.id }

        popupMenu.menu.add(0 ,ID_MOVE_UP, Menu.NONE ,context.getString(R.string.move_up))
        popupMenu.menu.add(0 , ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_up))
        popupMenu.menu.add(0 , ID_REMOVE, Menu.NONE, context.getString(R.string.move_up))
        popupMenu.menu.add(0 , ID_FIRE, Menu.NONE, context.getString(R.string.move_up))

//        popupMenu.setOnMenuItemClickListener {
//            when(it.itemId){
//                ID_MOVE_UP -> {
//
//                }
//            }
//        }

        popupMenu.show()

    }

    companion object{
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
        private const val ID_FIRE = 4
    }
}