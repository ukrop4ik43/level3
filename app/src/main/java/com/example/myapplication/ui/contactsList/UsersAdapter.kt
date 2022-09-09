package com.example.myapplication.ui.contactsList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.ui.itemDetailFragment
import com.example.myapplication.ui.model.User
import com.example.myapplication.ui.viewModel.SharedViewModel
import java.util.*


interface UserActionListener {
    fun onUserDelete(userPosition: Int)
    fun getName()

}

class UsersAdapter(private val actionListener: UserActionListener,
                   private val sharedViewModel: SharedViewModel) :
    ListAdapter<User, UsersAdapter.UsersViewHolder>(object : DiffUtil.ItemCallback<User>() {


        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding, actionListener)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bindTo(getItem(position))
        holder.itemView.setOnClickListener { v ->
            val activity = v!!.context as AppCompatActivity
            val itemDetailFragment = itemDetailFragment()
            sharedViewModel.saveUserName(actionListener.getUserName(absoluteAdapterPosition))
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.startFragment, itemDetailFragment).addToBackStack(null)
                .commit()
        }
    }


    class UsersViewHolder(
        private val binding: ItemUserBinding,
        private val userActionListener: UserActionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(user: User) {
            with(binding) {
                nameSurnameTV.text = user.name
                careerTV.text = user.occupy
                if (user.photo.isNotBlank()) {
                    avatarIV.addImage(user)
                } else {
                    avatarIV.setImageResource(R.drawable.me)
                }
            }
            setListeners()
        }

        private fun setListeners() {
            binding.trashBinIB.setOnClickListener {
                userActionListener.onUserDelete(absoluteAdapterPosition)
                Toast.makeText(it.context, "Deleted", Toast.LENGTH_LONG).show()

            }
        }
    }
}

private fun ImageView.addImage(user: User) {
    Glide.with(this.context)
        .load(user.photo)
        .circleCrop()
        .placeholder(R.drawable.me)
        .error(R.drawable.me)
        .into(this)
}
