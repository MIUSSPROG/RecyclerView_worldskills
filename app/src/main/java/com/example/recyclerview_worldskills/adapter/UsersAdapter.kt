package com.example.recyclerview_worldskills.adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.example.recyclerview_worldskills.R
import com.example.recyclerview_worldskills.databinding.ItemUserBinding
import com.example.recyclerview_worldskills.model.User


interface UserActionListener {
    fun onUserMove(user: User, moveBy: Int)

    fun onUserDelete(user: User)

    fun onUserDetails(user: User)
}

class UsersDiffCallback(
    private val oldList: List<User>,
    private val newList: List<User>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.id == newUser.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser == newUser
    }
}


class UsersAdapter(
    private val actionListener: UserActionListener
) : RecyclerView.Adapter<UsersAdapter.UsersViewHolder>(), View.OnClickListener {

    var users: List<User> = emptyList()
        set(newValue) {
            val diffCallback = UsersDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.imgvMore.setOnClickListener(this)

        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = users[position]
        val context = holder.itemView.context
        with(holder.binding) {
            holder.itemView.tag = user
            imgvMore.tag = user

            tvUserName.text = user.name
            tvCompany.text =
                if (user.company.isNotBlank()) user.company else context.getString(R.string.Unemployed)
            if (user.photo.isNotBlank()) {
                Glide.with(imgvPhoto.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .error(R.drawable.ic_baseline_account_circle_24)
                    .into(imgvPhoto)
            } else {
                imgvPhoto.setImageResource(R.drawable.ic_baseline_account_circle_24)
            }
        }

    }

    override fun getItemCount(): Int = users.size

    class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
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

        popupMenu.menu.add(0, ID_MOVE_UP, Menu.NONE, context.getString(R.string.move_up)).apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, ID_MOVE_DOWN, Menu.NONE, context.getString(R.string.move_down))
            .apply {
                isEnabled = position < users.size - 1
            }
        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, context.getString(R.string.remove))
        popupMenu.menu.add(0, ID_FIRE, Menu.NONE, context.getString(R.string.fire))

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_MOVE_UP -> {
                    actionListener.onUserMove(user, -1)
                }
                ID_MOVE_DOWN -> {
                    actionListener.onUserMove(user, 1)
                }
                ID_REMOVE -> {
                    actionListener.onUserDelete(user)
                }
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()

    }

    companion object {
        private const val ID_MOVE_UP = 1
        private const val ID_MOVE_DOWN = 2
        private const val ID_REMOVE = 3
        private const val ID_FIRE = 4
    }
}