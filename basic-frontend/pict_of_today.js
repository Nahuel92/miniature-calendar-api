'use strict';

const e = React.createElement;

class TemplateComponent {
    constructor(cmp) {
        this.cmp = cmp;
    }

    render() {
        if (this.cmp.state.error) {
            return errorMessage(this.cmp, this.cmp.errorMessage());
        }

        if (!this.cmp.state.isPressed) {
            return this.cmp.showButton();
        }

        if (!this.cmp.state.loaded) {
            return e('div', {}, 'Loading...');
        }

        showData(this.cmp.state);
        return this.cmp.displayData();
    }
}

class PicturesOfTodayButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {items: [], error: null, isPressed: false, loaded: false};
    }

    render() {
        return new TemplateComponent(this).render();
    }

    errorMessage() {
        return 'Hide Pictures of the Day';
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
            'Show Pictures of the Day'
        );
    }

    displayData() {
        return e('div',
            {},
            e('button',
                {onClick: () => {this.setState({isPressed: false, items: [], loaded: false});
                        hideData();}
                },
                'Hide Pictures of the Day')
        );
    }
}

const domContainer = document.querySelector('#pictures_of_today_button_container');
ReactDOM.render(e(PicturesOfTodayButton), domContainer);
