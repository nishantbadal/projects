class AccessController < ApplicationController
  def menu
      if session[:user_id] != nil
    @myAccount = User.find_by(id: session[:user_id])
    @user = session[:user_id]
    @posts = Post.where(:user_id => @myAccount.id)
    @likes = Like.all
      else
          redirect_to(root_path)
      end
  end

  def login
      if session['user_id'] != nil
          flash[:notice] = "Log out to sign in to another account."
          redirect_to(root_path)
      else
      end
      
  end
  
  def signup
      @user = session[:user_id]
      if @user == nil
          
      else
          flash[:notice] = "Log out to create a new account."
          redirect_to(root_path)
      end
  end 
    
  def signupAttempt
      @user = session[:user_id]
      if @user == nil
      if params[:name].present? && params[:password].present? && params[:email].present?
            searchUser = User.where(:name => params[:name]).first
            if searchUser
               authorizeUser = searchUser.authenticate(params[:password])
            end
        end
      
      if authorizeUser
            flash.now[:notice] = "User already exists."
            render('signup')
        else
            newUser = User.new
            newUser.name = params[:name]
            newUser.email = params[:email]
            newUser.password = params[:password]
            newUser.save
          
            session[:user_id] = newUser.id
            flash[:notice] = "Logged in successfully."
            redirect_to(admin_path)
        end
      else
          flash[:notice] = "Log out to create a new account."
          redirect_to(root_path)
      end
          
  end
    
    
    def attempt
        if params[:name].present? && params[:password].present?
            searchUser = User.where(:name => params[:name]).first
            if searchUser
               authorizeUser = searchUser.authenticate(params[:password])
            end
        end
        
        if authorizeUser
            session[:user_id] = authorizeUser.id
            flash[:notice] = "Logged in successfully."
            redirect_to(admin_path)
        else
            flash.now[:notice] = "Invalid username/password."
            render('login')
        end
    end
    
    def logout
        if session['user_id'] != nil
        session['user_id'] = nil
        flash[:notice] = 'Logged out.'
        else
           flash[:notice] = 'You are already not logged in.' 
        end    
        redirect_to(access_login_path)
    end
    
    def help
    end
    
end
