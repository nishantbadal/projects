class LikesController < ApplicationController
    
    def index
    end
    
    def create
        if session['user_id'] != nil
        l = Like.new
        l.post_id = params["post_id"]
        p = Post.find_by(id: params["post_id"])
        l.user_id = p.user_id
        l.save
        redirect_to "/", notice: 'Successfully liked.'
        else
            flash[:notice] = "Log in to like."
          redirect_to(root_path)
        end
    end
    
    def destroy
        if session['user_id'] != nil
        l = Like.find_by(post_id: params["post_id"])
        l.destroy
        redirect_to "/", notice: 'Successfully unliked.'
        else
           flash[:notice] = "Log in to like."
          redirect_to(root_path) 
        end
    end
    
end