class PostsController < ApplicationController
    
    
    def index
		@posts = Post.all
        @likes = Like.all
        @user = session[:user_id]
    end
	
 
 	def new
        @user = session[:user_id]
        if @user == nil
            flash[:notice] = "Log in to post."
          redirect_to(root_path)
        else    
 		@post = Post.new :user_id => session[:user_id]
        end
 	end
 
 	def create
        @user = session[:user_id]
        if @user != nil
 		@post = Post.new(post_params)
 		if @post.save
 			redirect_to posts_path
 		else
 			render :new
 		end
        else
          flash[:notice] = "Log in to post."
          redirect_to(root_path)
        end
 	end
    
    def show
        @p = Post.where(:id => params["id"])
        @likes = Like.all
        @user = session[:user_id] 
    end
    
    def edit
        @p = Post.where(:id => params["id"]).first
        if session[:user_id] == @p.user_id
        
        else
          flash[:notice] = "You do not have permission to edit this post."
          redirect_to(root_path)
        end
    end
    
    def update
    p = Post.find_by(id: params["id"])
        
    if session[:user_id] == p.user_id
    p.title = params["title"]
    p.description = params["description"]
    p.save
    redirect_to "/posts/#{p.id}", notice: 'Post successfully updated.'
    else
        redirect_to "/posts/#{p.id}", notice: 'You do not have permission to edit this post.'
    end
        
        
    end
    
    def destroy
        p = Post.find_by(id: params["id"])
        
        if session[:user_id] == p.user_id
        p.destroy
        redirect_to "/access/menu", notice: 'Successfully deleted your post.'
        else
            redirect_to "/posts/#{p.id}", notice: 'You do not have permission to edit this post.'
        end
    end
 
    private 
 	def post_params
 		params.require(:post).permit(:title, :description, :user_id, :image)
 	end
        
    
end