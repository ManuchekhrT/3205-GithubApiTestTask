package tj.githubdownloadrepotesttask.presentation.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tj.githubdownloadrepotesttask.databinding.ActivityMainBinding
import tj.githubdownloadrepotesttask.presentation.model.Repository
import tj.githubdownloadrepotesttask.presentation.model.State
import tj.githubdownloadrepotesttask.presentation.ui.downloaded_repositories.DownloadedReposFragment
import tj.githubdownloadrepotesttask.presentation.ui.downloaded_repositories.TAG_DownloadedReposFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: RepositoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupViews()
        setupRecyclerView()
        setupSearchView()
        observeRepos()
    }

    private fun setupViews() {
        with(binding) {
            ivDownloaded.setOnClickListener {
                DownloadedReposFragment().show(supportFragmentManager, TAG_DownloadedReposFragment)
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setupRecyclerView() {
        adapter = RepositoryAdapter {
            downloadFile(it)
        }
        binding.rvRepos.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRepos.adapter = adapter
    }

    private fun downloadFile(repo: Repository) {
        viewModel.downloadRepo(repo)
    }

    private fun observeRepos() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userReposState.collect {
                    when (it) {
                        is State.Success -> {
                            binding.progressBar.isVisible = false
                            binding.tvSearchData.isVisible = false
                            adapter.submitList(it.data)
                        }
                        is State.Error -> {
                            binding.progressBar.isVisible = false
                            binding.tvSearchData.isVisible = false
                            Toast.makeText(this@MainActivity, it.message ?: "", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is State.Loading -> {
                            binding.tvSearchData.isVisible = false
                            binding.progressBar.isVisible = true
                        }
                        is State.Empty -> {
                            binding.progressBar.isVisible = false
                            binding.tvSearchData.isVisible = true
                        }
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.downloadComplete.collect {
                    if (it.second) {
                        val position = adapter.currentList.indexOf(it.first)
                        val viewHolder =
                            binding.rvRepos.findViewHolderForAdapterPosition(position) as? RepositoryAdapter.RepoViewHolder
                        viewHolder?.onDownloadComplete()
                    }
                }
            }
        }

    }
}