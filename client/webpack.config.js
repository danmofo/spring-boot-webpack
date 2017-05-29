var webpack = require('webpack');
var path = require('path');
var glob = require('glob');

var ExtractTextPlugin = require("extract-text-webpack-plugin");
var PurifyCSSPlugin = require('purifycss-webpack');
var CleanWebpackPlugin = require('clean-webpack-plugin');
var ManifestPlugin = require('webpack-manifest-plugin');
var WebpackShellPlugin = require('webpack-shell-plugin');

var inProduction = process.env.NODE_ENV === 'production';

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    source: path.join(__dirname, 'src/scripts'),
    output: path.join(__dirname, '../../../target/classes/static'),
    templates: path.join(__dirname, '../server/target/classes/templates/')
}

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
	module: {
		rules: [
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
				loaders: [
					{
						loader: 'file-loader',
						options: {
							name: 'images/[name].[hash].[ext]'
						}
					},
          // todo: make this conditional..
          // 'img-loader'
				]
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
      // filename: "styles/[name].css"
		}),
		new PurifyCSSPlugin({
			paths: glob.sync(PATHS.templates + '*.ftl'),
			minimize: inProduction
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
      dev: false,
      // todo: only copy to /resources on build..
      onBuildEnd: ['cp dist/manifest.json ../server/src/main/resources/',
                   'cp dist/manifest.json ../server/target/classes/']
    })
	],
  devServer: {
      port: 9090,
      proxy: {
          '/': {
              target: 'http://localhost:8080',
              secure: false,
              prependPath: false
          }
      },
      publicPath: 'http://localhost:9090/',
      historyApiFallback: true
  }
}

if(inProduction) {
	module.exports.plugins.push(
		new webpack.optimize.UglifyJsPlugin()
	)
}
