module.exports = {
	title: 'fc blog',
	description: '我的个人网站',
	head: [ // 注入到当前页面的 HTML <head> 中的标签
		[
			'link', {
				rel: 'icon',
				href: '/logo.jpg'
			}

		] // 增加一个自定义的 favicon(网页标签的图标)
	],
	base: '/vuepress-demo/', // 这是部署到github相关的配置
	markdown: {
		lineNumbers: true // 代码块显示行号
	},
	themeConfig: {
		sidebarDepth: 3,
		nav: [ // 导航栏配置
			{
				text: '博客',
				link: '/blog/'
			}
		],
		sidebar: {
			'/blog/': [{
				title: '文档',
				collapsable: false,
				children: [
					'document/vuepress',
				]
			}]

		}
	}
};
