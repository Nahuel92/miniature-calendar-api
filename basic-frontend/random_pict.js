'use strict';

class RandomDatePicturesButton extends React.Component {
    constructor(props) {
        super(props);
        this.hideText = 'Hide Random Pictures of the Day';
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
                    fetchData(this, null, true);
                }
            },
            'Show Random Pictures of the Day'
        );
    }

    displayData() {
        return e('div',
            {},
            e('button',
                {
                    onClick: () => {
                        this.setState({isPressed: false, loaded: false, items: []});
                        hideData();
                    }
                },
                this.hideText)
        );
    }
}

const dom2Container = document.querySelector('#random_pictures_button_container');
ReactDOM.render(e(RandomDatePicturesButton), dom2Container);
