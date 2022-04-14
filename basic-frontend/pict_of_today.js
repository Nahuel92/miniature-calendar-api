'use strict';

class TemplateComponent {
    constructor(cmp) {
        this.cmp = cmp;
    }

    render() {
        if (this.cmp.state.error) {
            return e('div',
                {},
                this.cmp.state.error.error,
                e('button',
                    {onClick: () => this.cmp.setState({error: null, isPressed: false})},
                    this.cmp.hideText
                )
            );
        }

        if (!this.cmp.state.isPressed) {
            return this.cmp.showButton();
        }

        if (!this.cmp.state.loaded) {
            return e('div', {}, 'Loading...');
        }

        const items = this.cmp.state.items.map(function (item) {
            return [
                e('img',
                    {src: `data:image/jpg;base64,${item.picture}`, key: item.url + item.url + item.url}
                ),
                e('a', {href: item.url, className: 'lnk', key: item.url}, item.url),
                e('hr', {className: 'img-hr', key: item.url + item.url}),
            ];
        });
        ReactDOM.render(items, contentDiv);

        return e('div',
            {},
            e('button',
                {
                    onClick: () => {
                        this.cmp.setState({date: null, items: [], isPressed: false, loaded: false});
                        ReactDOM.render(e('div', {}), contentDiv);
                    }
                },
                this.cmp.hideText)
        );
    }
}

class PicturesOfTodayButton extends React.Component {
    constructor(props) {
        super(props);
        this.hideText = 'Hide Today\'s Picture/s';
        this.state = {items: [], error: null, isPressed: false, loaded: false};
    }

    render() {
        return new TemplateComponent(this).render();
    }

    showButton() {
        return e(
            'button',
            {
                onClick: () => {
                    this.setState({isPressed: true});
                    fetchData(this);
                }
            },
            'Show Today\'s Picture/s'
        );
    }
}

const e = React.createElement;
const contentDiv = document.querySelector('#content');
const domContainer = document.querySelector('#pictures_of_today_button_container');
ReactDOM.render(e(PicturesOfTodayButton), domContainer);
