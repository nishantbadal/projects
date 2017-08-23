Rails.application.routes.draw do
  root 'posts#index'
  resources :posts
  get 'posts/:id' => 'posts#show'
     
  get 'admin', :to => 'access#menu'
  get 'access/menu'
  get 'access/login'
  get 'access/signup'
  post 'access/signupAttempt'
  post 'access/attempt'
  get 'access/logout'
    get 'help', :to => 'access#help'
    get 'access/help'
    
  get 'users/' => 'users#show'
    
  resources :posts do
   resources :likes
  end

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
    
    
    
end
