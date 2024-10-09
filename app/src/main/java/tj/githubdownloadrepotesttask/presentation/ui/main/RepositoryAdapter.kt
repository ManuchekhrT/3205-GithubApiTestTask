package tj.githubdownloadrepotesttask.presentation.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.githubdownloadrepotesttask.R
import tj.githubdownloadrepotesttask.databinding.ItemRepoBinding
import tj.githubdownloadrepotesttask.presentation.model.Repository

class RepositoryAdapter(private val onDownloadClick: (model: Repository) -> Unit) : ListAdapter<Repository, RepositoryAdapter.RepoViewHolder>(RepoDiffCallBack()) {

    private class RepoDiffCallBack : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val view = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepoViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val model = currentList[position]
        model?.let {
            holder.onBind(it)
        }
    }

    inner class RepoViewHolder(private val itemRepoBinding: ItemRepoBinding) :
        RecyclerView.ViewHolder(itemRepoBinding.root) {

        fun onBind(model: Repository) {
            itemRepoBinding.tvLogin.text = model.ownerLogin
            itemRepoBinding.tvRepoName.text = model.name
            itemRepoBinding.tvRepoUrl.text = model.htmlUrl
            itemRepoBinding.ivDownloadIcon.setOnClickListener {
                itemRepoBinding.ivDownloadIcon.isVisible = false
                itemRepoBinding.pbDownloading.isVisible = true
                itemRepoBinding.tvDownload.text =
                    itemRepoBinding.root.context.getString(R.string.downloading)
                onDownloadClick.invoke(model)
            }
        }

        fun onDownloadComplete() {
            itemRepoBinding.pbDownloading.isVisible = false

            itemRepoBinding.ivDownloadIcon.setImageResource(R.drawable.ic_file_download_done)
            itemRepoBinding.ivDownloadIcon.isVisible = true

            itemRepoBinding.tvDownload.text =
                itemRepoBinding.root.context.getString(R.string.downloaded)
        }
    }
}