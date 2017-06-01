const webpack = require('webpack');
const path = require('path');
const glob = require('glob');

const ExtractTextPlugin = require("extract-text-webpack-plugin");
const PurifyCSSPlugin = require('purifycss-webpack');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const ManifestPlugin = require('webpack-manifest-plugin');
const WebpackShellPlugin = require('webpack-shell-plugin');

const inProduction = process.env.NODE_ENV === 'production';

module.exports = {
	entry: {
		app: [
		    './src/scripts/main.js',
		    './src/styles/main.scss'
		],
		vendor: 'jquery'
	},
	output: {
		path: path.resolve(__dirname + '/dist'),
		filename: 'scripts/[name].[chunkhash].js',
		publicPath: '/'
	},
  resolve: {
    alias: {
      'vue$': 'vue/dist/vue.esm.js'
    }
  },
	module: {
		rules: [
      {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {}
      },
			{
				test: /\.js$/,
				exclude: /node_modules/,
				use: ['babel-loader']
			},
			{
				test: /\.scss$/,
				use: ExtractTextPlugin.extract({
					use: [{
						loader: 'css-loader'
					},
					{
						loader: 'sass-loader'
					}]
				})
			},
			{
				test: /\.html$/,
				loader: 'file-loader'
			},
			{
				test: /\.(png|jpg|gif|svg)$/,
				loaders: (function() {
                     let imageLoaders = [
                         {
                             loader: 'file-loader',
                             options: {
                                 name: 'images/[name].[hash].[ext]'
                             }
                         }
                     ]

                     if(inProduction) {
                         imageLoaders.push('img-loader');
                     }

                     return imageLoaders;
                 })()
			},
			{
				test: /\.(eot|ttf|woff|woff2)$/,
				loader: 'file-loader',
				options: {
					limit: 1,
					name: 'fonts/[name].[hash].[ext]'
				}

			}
		]
	},
	plugins: [
		new CleanWebpackPlugin(['dist'], {
			root: __dirname,
			verbose: true,
			dry: false
		}),
		new ExtractTextPlugin({
			filename: "styles/[name].[hash].css"
		}),
		new webpack.LoaderOptionsPlugin({
			minimize: inProduction
		}),
        new ManifestPlugin({
            writeToFileEmit: true,
            basePath: '/',
            stripSrc: true
        }),
        new WebpackShellPlugin({
            dev: inProduction,
            onBuildEnd: (function() {
                let commands = ['cp dist/manifest.json ../server/target/classes/']
                if(inProduction) {
                    commands.push('cp dist/manifest.json ../server/src/main/resources/');
                }
                return commands;
            })()
         })
	],
    devServer: {
        port: 9090,
        proxy: [{
            context: '/cause/select',
            target: 'https://www.giveasyoulive.com/',
            secure: false,
            bypass: function(req, res, opts) {
                req.headers.host = 'www.giveasyoulive.com';
            }
        }, {
          context: '/',
          target: 'http://localhost:8080/',
          secure: false
        }],
        publicPath: 'http://localhost:9090/',
        historyApiFallback: true
    }
}

let plugins = module.exports.plugins;

if(inProduction) {
	plugins.push(new webpack.optimize.UglifyJsPlugin());
	plugins.push(new PurifyCSSPlugin({
                 	paths: glob.sync(path.join(__dirname, '../server/target/classes/templates/') + '*.ftl'),
                 	minimize: true
                 }));
}
