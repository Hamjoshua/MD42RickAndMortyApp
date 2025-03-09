package com.example.md42_rickandmortyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.md42_rickandmortyapp.databinding.RItemAlienBinding
import com.example.md42_rickandmortyapp.databinding.RItemHumanBinding
import com.example.md42_rickandmortyapp.databinding.RItemUnknownBinding


class CharacterAdapter(private val characters: ArrayList<Character>) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>()
{
    companion object {
        const val VIEW_TYPE_HUMAN : Int = 0
        const val VIEW_TYPE_ALIEN : Int = 1
        const val VIEW_TYPE_OTHER : Int = 2
    }

    abstract class CharacterViewHolder(private val itemBinding: ViewBinding) :
            RecyclerView.ViewHolder(itemBinding.root)
    {
        abstract fun bind(character: Character)
    }

    class HumanViewHolder(private val itemBinding: RItemHumanBinding) :
        CharacterViewHolder(itemBinding)
    {
        override fun bind(character: Character){
            character.setImageTo(itemBinding.avatarImage)
            itemBinding.nameTv.text = character.name
            itemBinding.typeTv.text = character.species
            itemBinding.genderTv.text = character.gender
        }
    }

    class AlienViewHolder(private val itemBinding : RItemAlienBinding) :
        CharacterViewHolder(itemBinding)
    {
        override fun bind(character: Character) {
            character.setImageTo(itemBinding.avatarImage)
            itemBinding.nameTv.text = character.name
            itemBinding.idTv.text = character.id
            itemBinding.typeTv.text = character.species
        }
    }
    class UnknownViewHolder(private val itemBinding : RItemUnknownBinding) :
        CharacterViewHolder(itemBinding)
    {
        override fun bind(character: Character) {
            character.setImageTo(itemBinding.avatarImage)
            itemBinding.nameTv.text = character.name
            itemBinding.idTv.text = character.id
            itemBinding.typeTv.text = character.species
            itemBinding.genderTv.text = character.gender
            itemBinding.statusTv.text = character.status
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (characters[position].species) {
            "Human" -> VIEW_TYPE_HUMAN
            "Alien" -> VIEW_TYPE_ALIEN
            else -> VIEW_TYPE_OTHER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
       return when(viewType){
           VIEW_TYPE_HUMAN -> {
               val itemBinding = RItemHumanBinding.inflate(LayoutInflater.from(parent.context),
                   parent, false)
               return HumanViewHolder(itemBinding)
           }
           VIEW_TYPE_ALIEN -> {
               val itemBinding = RItemAlienBinding.inflate(LayoutInflater.from(parent.context),
                   parent, false)
               return AlienViewHolder(itemBinding)
           }
           else -> {
               val itemBinding = RItemUnknownBinding.inflate(LayoutInflater.from(parent.context),
                   parent, false)
               return UnknownViewHolder(itemBinding)
           }
       }
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character: Character = characters[position]
        holder.bind(character)
    }

}